package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.AddSupplierProductDto
import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.internal.DiscoveryService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.CartSupplierProductRepository
import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartSupplierProduct
import com.sudoo.cartservice.repository.entity.SupplierProductKey
import com.sudoo.cartservice.service.CartService
import com.sudoo.cartservice.service.CartSupplierProductService
import com.sudoo.domain.utils.IdentifyCreator
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartSupplierProductServiceImpl(
        val cartService: CartService,
        val cartRepository: CartRepository,
        val cartSupplierProductRepository: CartSupplierProductRepository,
        val discoveryService: DiscoveryService
) : CartSupplierProductService {
    override suspend fun addSupplierProductToCart(userId: String, addSupplierProductDto: AddSupplierProductDto): CartDto {
        val cart: Cart = getActiveCart(userId)
        val supplierProductKey = SupplierProductKey(addSupplierProductDto.productId
                ?: "", addSupplierProductDto.supplierId ?: "", cart.cartId ?: "")
        val optionalCartSupplierProduct: CartSupplierProduct? = cartSupplierProductRepository.findById(supplierProductKey)
        val cartSupplierProduct: CartSupplierProduct
        var totalPrice: Double = cart.totalPrice ?: 0.0
        var totalAmount: Int = cart.totalAmount ?: 0
        if (optionalCartSupplierProduct!=  null) {
            cartSupplierProduct = optionalCartSupplierProduct
            cartSupplierProduct.amount = (cartSupplierProduct.amount ?: 0) + (addSupplierProductDto.amount ?: 0)
            val price: Double = getTotalPriceBySupplierProduct(addSupplierProductDto)
            cartSupplierProduct.totalPrice = (cartSupplierProduct.totalPrice ?: 0.0) + price
            totalAmount += addSupplierProductDto.amount ?: 0
            totalPrice += price
        } else {
            cartSupplierProduct = CartSupplierProduct(
                    supplierProductKey,
                    addSupplierProductDto.amount ?: 0,
                    getTotalPriceBySupplierProduct(addSupplierProductDto),
                    cart
            )
            totalAmount += addSupplierProductDto.amount ?: 0
            totalPrice += cartSupplierProduct.totalPrice ?: 0.0
        }
        cartSupplierProductRepository.save(cartSupplierProduct)

        var supplierProductList: MutableList<CartSupplierProduct?> = ArrayList()

        if (cart.cartSupplierProduct != null) supplierProductList = cart.cartSupplierProduct.toMutableList()
        supplierProductList.add(cartSupplierProduct)

        cart.cartSupplierProduct = supplierProductList.toList()

        cart.totalPrice = (totalPrice)
        cart.totalAmount = (totalAmount)
        cartRepository.save(cart)

        return CartDto(
                cart.cartId ?: "",
                cart.totalPrice ?: 0.0,
                cart.totalAmount ?: 0,
                cart.status ?: "",
                cartService.getSupplierProduct(userId, cart.cartId ?: "", cart.cartSupplierProduct, false)
        )
    }

    override suspend fun updateSupplierProductToCart(userId: String, cartId: String, addSupplierProductDtoList: List<AddSupplierProductDto>): CartDto {
        val cart: Cart = cartRepository.findById(cartId) ?: Cart()

        var totalPrice: Double = cart.totalPrice ?: 0.0
        var totalAmount: Int = cart.totalAmount ?: 0

        for (addSupplierProductDto in addSupplierProductDtoList) {
            val index: Int = getCartSupplierProductIndex(cart.cartSupplierProduct, addSupplierProductDto)
            val cartSupplierProduct: CartSupplierProduct? = cart.cartSupplierProduct.get(index)
            val currentAmount: Int = cartSupplierProduct?.amount ?: 0
            val currentPrice: Double = getPriceBySupplierProduct(addSupplierProductDto)
            val updateAmount: Int = addSupplierProductDto.amount ?: 0
            cartSupplierProduct?.amount = (updateAmount)
            cartSupplierProduct?.totalPrice = (updateAmount * currentPrice)
            totalAmount += updateAmount - currentAmount
            totalPrice += (updateAmount - currentAmount) * currentPrice
            cart.cartSupplierProduct.toMutableList().add(index, cartSupplierProduct)
        }
        cart.totalPrice = (totalPrice)
        cart.totalAmount = (totalAmount)
        cartRepository.save(cart)
        return CartDto(
                cart.cartId ?: "",
                cart.totalPrice ?: 0.0,
                cart.totalAmount ?: 0,
                cart.status ?: "",
                cartService.getSupplierProduct(userId, cart.cartId ?: "", cart.cartSupplierProduct, false)
        )
    }


    override suspend fun deleteSupplierProduct(userId: String?, cartId: String?, supplierId: String?, productId: String?): CartDto {
        val cart: Cart = cartRepository.findById(cartId ?: "") ?: Cart()
        val index: Int = getIndexCartSupplierProduct(cart, supplierId ?: "", productId ?: "")
        if (index == -1) throw java.lang.Exception("Not found item")
        val currentList: MutableList<CartSupplierProduct?> = cart.cartSupplierProduct.toMutableList()
        val cartSupplierProduct: CartSupplierProduct? = currentList.removeAt(index)
        cart.cartSupplierProduct = (currentList)
        cart.totalAmount = (cart.totalAmount ?: 0) - (cartSupplierProduct?.amount ?: 0)
        cart.totalPrice = (cart.totalPrice ?: 0.0) - (cartSupplierProduct?.totalPrice ?: 0.0)
        cartSupplierProductRepository.deleteById(SupplierProductKey(productId, supplierId, cartId))
        cartRepository.save(cart)
        return CartDto(
                cart.cartId ?: "",
                cart.totalPrice ?: 0.0,
                cart.totalAmount ?: 0,
                cart.status ?: "",
                cartService.getSupplierProduct(userId!!, cart.cartId ?: "", cart.cartSupplierProduct, false)
        )
    }

    private suspend fun getActiveCart(userId: String): Cart {
        return try {
            var cart: Cart = Cart()
            cartRepository.findByUserIdAndStatus(userId, "active").collect {
                cart = it
            }
            return cart
        } catch (e: Exception) {
            createNewCart(userId)
        }
    }

    private suspend fun createNewCart(userId: String): Cart {
        val cart = Cart()
        cart.cartId = IdentifyCreator.create()
        cart.userId = (userId)
        cart.status = ("active")
        cart.totalAmount = (0)
        cart.totalPrice = (0.0)
        return cartRepository.save(cart)
    }

    private fun getIndexCartSupplierProduct(cart: Cart, supplierId: String, productId: String): Int {
        for (i in 0 until cart.cartSupplierProduct.size) {
            val cartSupplierProduct: CartSupplierProduct? = cart.cartSupplierProduct[i]
            if ((cartSupplierProduct?.id?.supplierId ?: "").equals(supplierId) &&
                    (cartSupplierProduct?.id?.productId ?: "").equals(productId)) {
                return i
            }
        }
        return -1
    }


    private fun getCartSupplierProductIndex(cartSupplierProducts: List<CartSupplierProduct?>, addSupplierProductDto: AddSupplierProductDto): Int {
        for (i in cartSupplierProducts.indices) {
            if ((cartSupplierProducts[i]?.id?.productId ?: "").equals(addSupplierProductDto.productId ?: "") &&
                    (cartSupplierProducts[i]?.id?.supplierId ?: "").equals(addSupplierProductDto.supplierId ?: "")) {
                return i
            }
        }
        return -1
    }

    private fun getTotalPriceBySupplierProduct(s: AddSupplierProductDto): Double {
        return s.amount * getPriceBySupplierProduct(s)
    }

    private fun getPriceBySupplierProduct(s: AddSupplierProductDto): Double {
        return discoveryService.getSupplierProductPrice(s.supplierId ?: "", s.productId ?: "")
    }

}