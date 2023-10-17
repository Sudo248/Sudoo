package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.Pagination
import com.sudoo.domain.exception.BadRequestException
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.domain.validator.ProductValidator
import com.sudoo.productservice.dto.*
import com.sudoo.productservice.mapper.*
import com.sudoo.productservice.model.CategoryProduct
import com.sudoo.productservice.model.Image
import com.sudoo.productservice.repository.*
import com.sudoo.productservice.service.ProductService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.joinAll
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
        val categoryProduct = categoryProductRepository.getByProductIdAndCategoryId(
            categoryProductDto.productId,
            categoryProductDto.categoryId
        )
            ?: throw NotFoundException("Not found")
        categoryProductRepository.deleteCategoryProductByCategoryIdAndProductId(
            categoryId = categoryProductDto.categoryId,
            productId = categoryProductDto.productId,
        )
        return categoryProduct.toCategoryProductDto()
    }

    override suspend fun upsertProduct(userId: String, productDto: UpsertProductDto): UpsertProductDto =
        coroutineScope {
            validateUpsertProduct(productDto)
            val supplier =
                supplierRepository.getByUserId(userId)
                    ?: throw NotFoundException("Not found supplier of user $userId")
            val product = productDto.toProduct(supplierId = supplier.supplierId, brand = supplier.brand)
            productRepository.save(product)
            if (product.isNew) {
                productDto.categoryIds?.map { categoryId ->
                    launch {
                        val categoryProduct =
                            CategoryProduct.from(productId = product.productId, categoryId = categoryId)
                        categoryProductRepository.save(categoryProduct)
                    }
                }
                productDto.images?.map { image ->
                    launch {
                        imageRepository.save(image.toImage(ownerId = product.productId))
                    }
                }
            }

            productDto.copy(productId = product.productId, sku = product.sku)
        }

    override suspend fun patchProduct(productDto: UpsertProductDto): UpsertProductDto {
        val oldProduct = productRepository.findById(
            productDto.productId ?: throw BadRequestException("require product id")
        )
        val product = productDto.combineProduct(
            oldProduct ?: throw NotFoundException("Not found product id=${productDto.productId}")
        )
        productRepository.save(product)
        return productDto
    }

    override suspend fun deleteProduct(productId: String): String {
        productRepository.deleteById(productId)
        return productId
    }

    override suspend fun getListProductInfo(
        offsetRequest: OffsetRequest
    ): ProductPagination<ProductInfoDto> = coroutineScope {
        val count = async { productRepository.count() }
        val products =
            productRepository.getProductInfoWithOffset(offset = offsetRequest.offset, limit = offsetRequest.limit)
                .map { product ->
                    async {
                        product.brand = supplierRepository.getBrand(product.supplierId)
                        product.images = imageRepository.getAllByOwnerId(product.productId).map { it.url }.toList()
                        product.toProductInfoDto()
                    }
                }
        ProductPagination(
            products = products.map { it.await() }.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + products.count() + 1,
                total = count.await()
            )
        )
    }

    override suspend fun getListProductInfoByCategory(
        categoryId: String, offsetRequest: OffsetRequest
    ): ProductPagination<ProductInfoDto> = coroutineScope {
        val count = async { categoryProductRepository.countProductOfCategory(categoryId) }
        val products = productRepository.getProductIdByCategoryIdWithOffset(
            categoryId = categoryId, offset = offsetRequest.offset, limit = offsetRequest.limit
        ).map { productId ->
            async {
                val productInfo = productRepository.getProductInfoById(productId) ?: throw NotFoundException("Not found product $productId")
                productInfo.brand = supplierRepository.getBrand(productInfo.supplierId)
                productInfo.images = imageRepository.getAllByOwnerId(productInfo.productId).map { it.url }.toList()
                productInfo.toProductInfoDto()
            }
        }
        ProductPagination(
            products = products.map { it.await() }.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + products.count() + 1,
                total = count.await()
            )
        )
    }

    override suspend fun getListProductInfoBySupplier(
        supplierId: String,
        offsetRequest: OffsetRequest,
    ): ProductPagination<ProductInfoDto> = coroutineScope {
        val count = async { productRepository.countBySupplierId(supplierId) }
        val products = productRepository.getProductInfoBySupplierIdWithOffset(
            supplierId = supplierId, offset = offsetRequest.offset, limit = offsetRequest.limit
        ).map { product ->
            async {
                product.brand = supplierRepository.getBrand(supplierId)
                product.images = imageRepository.getAllByOwnerId(product.productId).map { it.url }.toList()
                product.toProductInfoDto()
            }
        }

        ProductPagination(
            products = products.map { it.await() }.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + products.count() + 1,
                total = count.await(),
            )
        )
    }

    override suspend fun getProductDetailById(productId: String): ProductDto = coroutineScope {
        val product = productRepository.findById(productId) ?: throw NotFoundException("Not found product $productId")
        joinAll(
            launch {
                product.supplier = supplierRepository.findById(product.supplierId)
                    ?: throw NotFoundException("Not found supplier of user ${product.supplierId}")
            },
            launch {
                product.images = imageRepository.getAllByOwnerId(productId).toList()
            },
            launch {
                product.categories = categoryProductRepository.getByProductId(productId).map {
                    categoryRepository.findById(it.categoryId)!!
                }.toList()
            }
        )

        product.toProductDto()
    }

    override suspend fun getProductInfoById(productId: String): ProductInfoDto = coroutineScope {
        val productInfo = productRepository.getProductInfoById(productId) ?: throw NotFoundException("Not found product $productId")
        joinAll(
            launch {
                productInfo.brand = supplierRepository.getBrand(productInfo.supplierId)
            },
            launch {
                productInfo.images = imageRepository.getAllByOwnerId(productInfo.productId).map { it.url }.toList()
            }
        )
        productInfo.toProductInfoDto()
    }

    override suspend fun getProductDetailBySku(sku: String): ProductDto = coroutineScope {
        val product = productRepository.getProductBySku(sku) ?: throw NotFoundException("Not found product sku $sku")
        joinAll(
            launch {
                product.supplier = supplierRepository.findById(product.supplierId)
                    ?: throw NotFoundException("Not found supplier of user ${product.supplierId}")
            },
            launch {
                product.images = imageRepository.getAllByOwnerId(product.productId).toList()
            },
            launch {
                product.categories = categoryProductRepository.getByProductId(product.productId).map {
                    categoryRepository.findById(it.categoryId)!!
                }.toList()
            }
        )

        product.toProductDto()
    }

    override suspend fun searchProductByName(
        userId: String, name: String, offsetRequest: OffsetRequest
    ): ProductPagination<ProductInfoDto> {
        TODO("Not yet implemented")
    }

    private fun validateUpsertProduct(productDto: UpsertProductDto) {
        if (!ProductValidator.validateNameProduct(productDto.name.orEmpty())) throw BadRequestException("Product's name is too short, require at least 5 character")
        if (!ProductValidator.validateAmount(productDto.amount)) throw BadRequestException("Product's amount must be more than 0")
        if (!ProductValidator.validatePrice(
                productDto.price,
                productDto.listedPrice
            )
        ) throw BadRequestException("Product's price or listed price must not be null and more than 0")
    }
}