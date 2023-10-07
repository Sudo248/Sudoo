package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartSupplierProductDto
import com.sudoo.cartservice.controller.dto.SupplierProductDto
import com.sudoo.cartservice.internal.DiscoveryService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.CartSupplierProductRepository
import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartSupplierProduct
import com.sudoo.cartservice.service.CartService
import com.sudoo.domain.utils.IdentifyCreator
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
        val cartRepository: CartRepository,
        val cartSupplierProductRepository: CartSupplierProductRepository,
        val discoveryService: DiscoveryService
) : CartService {
    override suspend fun createNewCart(userId: String): CartDto {
        var cart = Cart(
                cartId = IdentifyCreator.create(),
                userId = userId,
                totalAmount = 0,
                totalPrice = 0.0,
                status = "active",
        )
        var savedCart = cartRepository.save(cart)
        var cartDto = getCartById(userId = userId, cartId = savedCart.cartId ?: "", false)
        return cartDto
    }

    override suspend fun updateStatusCart(userId: String): CartDto {
        var updatingCart: CartDto = CartDto()
        cartRepository.findByUserIdAndStatus(userId, "active").collect { cart ->
            cart.status = "completed"
            var savedCart = cartRepository.save(cart)
            updatingCart = getCartById(userId, savedCart.cartId ?: "", false)
        }
        return updatingCart
    }

    override suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto {
        val cart: Cart = cartRepository.findById(cartId) ?: Cart()
        var totalPrice = 0.0
        var totalAmount = 0
        var cartSupplierProductDtoList: List<CartSupplierProductDto> = ArrayList()
        if (cart.cartSupplierProduct.isNotEmpty()) {
            for (cartSupplierProduct in cart.cartSupplierProduct) {
                totalPrice += cartSupplierProduct?.totalPrice!!
                totalAmount += cartSupplierProduct.amount!!
            }
            cartSupplierProductDtoList = getSupplierProduct(userId, cartId, cart.cartSupplierProduct, hasRoute)
        }
        return CartDto(
                cart.cartId ?: "",
                totalPrice,
                totalAmount,
                cart.status ?: "active",
                cartSupplierProductDtoList
        )
    }

    override suspend fun getActiveCartByUserId(userId: String): CartDto {
        return try {
            var cart: Cart = Cart()
            cartRepository.findByUserIdAndStatus(userId, "active").collect {
                cart = it
            }
            CartDto(
                    cart.cartId ?: "",
                    cart.totalPrice ?: 0.0,
                    cart.totalAmount ?: 0,
                    cart.status ?: "active",
                    getSupplierProduct(userId, cart.cartId ?: "", cart.cartSupplierProduct, false)
            )
        } catch (e: Exception) {
            createNewCart(userId)
        }
    }

    override suspend fun getCountItemActiveCart(userId: String): Int {
        var count = 0
        return try {
            cartSupplierProductRepository.countByCart_UserIdAndCart_Status(userId, "active").collect {
                count = it
            }
            count
        } catch (e: java.lang.Exception) {
            0
        }
    }

    override suspend fun getSupplierProduct(userId: String, cartId: String, list: List<CartSupplierProduct?>, hasRoute: Boolean): List<CartSupplierProductDto> {
        val supplierProductDtos: MutableList<CartSupplierProductDto> = java.util.ArrayList()
        for (s in list) {
            val supplierProductDto: SupplierProductDto = discoveryService.getSupplierProduct(
                    userId,
                    s?.id?.supplierId ?: "",
                    s?.id?.productId ?: "",
                    hasRoute
            )
            supplierProductDtos.add(CartSupplierProductDto(
                    supplierProductDto,
                    s?.amount ?: 0,
                    (s?.amount ?: 0) * supplierProductDto.price,
                    cartId
            ))
        }

        return supplierProductDtos
    }
}