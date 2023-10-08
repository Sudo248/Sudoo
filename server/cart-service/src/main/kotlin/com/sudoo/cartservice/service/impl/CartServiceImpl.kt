package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.repository.CartProductRepository
//import com.sudoo.cartservice.service.DiscoveryService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartProduct
import com.sudoo.cartservice.repository.entity.toCartProductDto
import com.sudoo.cartservice.service.CartService
import com.sudoo.domain.utils.IdentifyCreator
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
        val cartRepository: CartRepository,
        val cartProductRepository: CartProductRepository
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
        var cartDto = getCartById(userId = userId, cartId = savedCart.cartId, false)
        return cartDto
    }

    override suspend fun updateStatusCart(userId: String): CartDto {
        var cart = cartRepository.findCartByUserIdAndStatus(userId, "active")
        cart.status = "completed"
        var savedCart = cartRepository.save(cart)
        return getCartById(userId, savedCart.cartId, false)
    }

    /**
     * Lấy chính xác 1 cart của user
     */
    override suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto {
        val cart: Cart? = cartRepository.findById(cartId)
        var totalPrice = 0.0
        var totalAmount = 0
        var cartProductDtos: List<CartProductDto> = ArrayList()
        if (cart?.cartProducts?.isNotEmpty() == true) {
            for (cartProduct in cart.cartProducts) {
                totalPrice += cartProduct.totalPrice
                totalAmount += cartProduct.quantity
            }
            cartProductDtos = getCartProducts(userId, cartId,  hasRoute)
        }
        return CartDto(
                cart?.cartId ?: "",
                totalPrice,
                totalAmount,
                cart?.status ?: "active",
                cartProductDtos
        )
    }

    override suspend fun getActiveCartByUserId(userId: String): CartDto {
        return try {
            var cart: Cart? = cartRepository.findCartByUserIdAndStatus(userId, "active")
            CartDto(
                    cart?.cartId ?: "",
                    cart?.totalPrice ?: 0.0,
                    cart?.totalAmount ?: 0,
                    cart?.status ?: "active",
                    getCartProducts(userId, cart?.cartId ?: "", false)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            createNewCart(userId)
        }
    }

    override suspend fun getCountItemActiveCart(userId: String): Int {
        return try {
            cartRepository.countByUserIdAndStatus(userId, "active")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Lấy danh sách product trong 1 cart
     */
    override suspend fun getCartProducts(userId: String, cartId: String, hasRoute: Boolean): List<CartProductDto> {
        val cartProductDtos: MutableList<CartProductDto> = mutableListOf()
        val cartProductsOfCart = cartProductRepository.findCartProductByCartId(cartId).toList()
        for(cartProduct in cartProductsOfCart){
            cartProductDtos.add(cartProduct.toCartProductDto())
        }
        return cartProductDtos.toList()
    }
}