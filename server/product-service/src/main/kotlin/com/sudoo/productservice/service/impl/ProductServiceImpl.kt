package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.Pagination
import com.sudoo.domain.base.PaginationDto
import com.sudoo.domain.exception.BadRequestException
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.CategoryProductDto
import com.sudoo.productservice.dto.ProductDto
import com.sudoo.productservice.dto.ProductInfoDto
import com.sudoo.productservice.dto.UpsertProductDto
import com.sudoo.productservice.mapper.*
import com.sudoo.productservice.model.CategoryProduct
import com.sudoo.productservice.model.Image
import com.sudoo.productservice.repository.*
import com.sudoo.productservice.service.ProductService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val supplierRepository: SupplierRepository,
    private val categoryRepository: CategoryRepository,
    private val imageRepository: ImageRepository,
    private val categoryProductRepository: CategoryProductRepository,
) : ProductService {
    override suspend fun addProductToCategory(categoryProductDto: CategoryProductDto): CategoryProductDto {
        val categoryProduct = categoryProductDto.toCategoryProduct()
        categoryProductRepository.save(categoryProduct)
        return categoryProduct.toCategoryProductDto()
    }

    override suspend fun deleteProductOfCategory(categoryProductDto: CategoryProductDto): CategoryProductDto {
        categoryProductRepository.delete(categoryProductDto.toCategoryProduct())
        return categoryProductDto
    }

    override suspend fun upsertProduct(userId: String, productDto: UpsertProductDto): ProductDto = coroutineScope {
        val isUpdate = productDto.productId != null
        val supplier =
            supplierRepository.getByUserId(userId) ?: throw NotFoundException("Not found supplier of user $userId")
        val product = productDto.toProduct(supplierId = supplier.supplierId, brand = supplier.brand)
        productRepository.save(product)
        if (!isUpdate) {
            productDto.categoryIds?.map { categoryId ->
                launch {
                    val categoryProduct = CategoryProduct.from(productId = product.productId, categoryId = categoryId)
                    categoryProductRepository.save(categoryProduct)
                }
            }
            productDto.images?.map { url ->
                launch {
                    val image = Image.from(ownerId = product.productId, url = url)
                    imageRepository.save(image)
                }
            }
        }

        product.toProductDto()
    }

    override suspend fun patchProduct(productDto: UpsertProductDto): ProductDto {
        val oldProduct = productRepository.findById(
            productDto.productId ?: throw BadRequestException("require product id")
        )
        val product = productDto.combineProduct(
            oldProduct ?: throw NotFoundException("Not found product id=${productDto.productId}")
        )
        productRepository.save(product)
        return product.toProductDto()
    }

    override suspend fun deleteProduct(productId: String): String {
        productRepository.deleteById(productId)
        return productId
    }

    override suspend fun getListProductInfo(
        userId: String, offsetRequest: OffsetRequest
    ): PaginationDto<Flow<ProductInfoDto>> = coroutineScope {
        val count = async { productRepository.count() }
        val data = productRepository.getProductInfoWithOffset(offset = offsetRequest.offset, limit = offsetRequest.limit)
            .map { product ->
                product.brand = supplierRepository.getBrandByUserId(userId)
                product.images = imageRepository.getAllByOwnerId(product.productId).map { it.url }.toList()
                product.toProductInfoDto()
            }
        PaginationDto(
            data = data,
            pagination = Pagination(
                offset = offsetRequest.offset + data.count() + 1,
                total = count.await()
            )
        )
    }

    override suspend fun getListProductInfoByCategory(
        userId: String, categoryId: String, offsetRequest: OffsetRequest
    ): PaginationDto<Flow<ProductInfoDto>> = coroutineScope {
        val count = async { categoryProductRepository.countProductOfCategory(categoryId) }
        val data = productRepository.getProductIdByCategoryIdWithOffset(
            categoryId = categoryId, offset = offsetRequest.offset, limit = offsetRequest.limit
        ).map { productId ->
            val productInfo = productRepository.getProductInfoById(productId)
            productInfo.brand = supplierRepository.getBrandByUserId(userId)
            productInfo.toProductInfoDto()
        }
        PaginationDto(
            data = data,
            pagination = Pagination(
                offset = offsetRequest.offset + data.count() + 1,
                total = count.await()
            )
        )
    }

    override suspend fun getListProductInfoBySupplier(
        userId: String, supplierId: String, offsetRequest: OffsetRequest
    ): PaginationDto<Flow<ProductInfoDto>> = coroutineScope {
        val count = async { productRepository.countBySupplierId(supplierId) }
        val data = productRepository.getProductInfoBySupplierIdWithOffset(
            supplierId = supplierId, offset = offsetRequest.offset, limit = offsetRequest.limit
        ).map { product ->
            product.brand = supplierRepository.getBrandByUserId(userId)
            product.images = imageRepository.getAllByOwnerId(product.productId).map { it.url }.toList()
            product.toProductInfoDto()
        }

        PaginationDto(
            data = data,
            pagination = Pagination(
                offset = offsetRequest.offset + data.count() + 1,
                total = count.await(),
            )
        )
    }

    override suspend fun getProductDetailById(userId: String, productId: String): ProductDto = coroutineScope {
        val product =
            async { productRepository.findById(productId) ?: throw NotFoundException("Not found product $productId") }
        val supplier = async {
            val supplier =
                supplierRepository.getByUserId(userId) ?: throw NotFoundException("Not found supplier of user $userId")
            supplier.toSupplierInfoDto()
        }
        val images = async { imageRepository.getAllByOwnerId(productId).map { it.toImageDto() }.toList() }
        val categories = async {
            categoryProductRepository.getByProductId(productId).map {
                categoryRepository.findById(it.categoryId)!!
            }.map { category ->
                category.toCategoryInfoDto()
            }.toList()
        }
        product.await().toProductDto(
            supplier = supplier.await(),
            images = images.await(),
            categories = categories.await(),
        )
    }

    override suspend fun getProductDetailBySku(userId: String, sku: String): ProductDto = coroutineScope {
        val product = productRepository.getProductBySku(sku) ?: throw NotFoundException("Not found product sku $sku")
        val supplier = async {
            val supplier =
                supplierRepository.getByUserId(userId) ?: throw NotFoundException("Not found supplier of user $userId")
            supplier.toSupplierInfoDto()
        }
        val images = async { imageRepository.getAllByOwnerId(product.productId).map { it.toImageDto() }.toList() }
        val categories = async {
            categoryProductRepository.getByProductId(product.productId).map {
                categoryRepository.findById(it.categoryId)!!
            }.map { category ->
                category.toCategoryInfoDto()
            }.toList()
        }
        product.toProductDto(
            supplier = supplier.await(),
            images = images.await(),
            categories = categories.await(),
        )
    }

    override suspend fun searchProductByName(
        userId: String, name: String, offsetRequest: OffsetRequest
    ): PaginationDto<Flow<ProductInfoDto>> {
        TODO("Not yet implemented")
    }
}