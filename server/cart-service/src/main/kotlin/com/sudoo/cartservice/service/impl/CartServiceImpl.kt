package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.repository.CartProductRepository
//import com.sudoo.cartservice.service.ProductService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartStatus
import com.sudoo.cartservice.repository.entity.toCartProductDto
import com.sudoo.cartservice.service.CartService
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.domain.utils.IdentifyCreator
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
    val cartRepository: CartRepository,
    val cartProductRepository: CartProductRepository
) : CartService {
    override suspend fun createCartByStatus(userId: String, status: String): CartDto {
        //TODO: Check nếu đã tồn tại cart active thì trả về cart active đó
        val cart = Cart(
            cartId = IdentifyCreator.create(),
            userId = userId,
            totalAmount = 0,
            totalPrice = 0.0,
            status = when (status) {
                CartStatus.ACTIVE.value -> CartStatus.ACTIVE.value
                else -> CartStatus.COMPLETED.value
            },
        ).apply { isNewCart = true }
        val savedCart = cartRepository.save(cart)
        val cartDto = getCartById(userId = userId, cartId = savedCart.cartId, false)
        return cartDto
    }

    override suspend fun createNewCart(userId: String): CartDto {
        val cart = Cart(
            cartId = IdentifyCreator.create(),
            userId = userId,
            totalAmount = 0,
            totalPrice = 0.0,
            status = CartStatus.ACTIVE.value,
        ).apply { isNewCart = true }
        val savedCart = cartRepository.save(cart)
        val cartDto = getCartById(userId = userId, cartId = savedCart.cartId, false)
        return cartDto
    }

    override suspend fun updateStatusCart(userId: String): CartDto {
        val cart = cartRepository.findCartByUserIdAndStatus(userId, CartStatus.ACTIVE.value).toList()[0]
        cart.status = CartStatus.COMPLETED.value
        val savedCart = cartRepository.save(cart)
        return getCartById(userId, savedCart.cartId, false)
    }

    /**
     * Lấy chính xác 1 cart của user
     */
    override suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto {
        val cart = cartRepository.findById(cartId) ?: throw NotFoundException("Not found cart $cartId")
        val totalPrice = 0.0
        var totalAmount = 0
        var cartProductDtos: List<CartProductDto> = ArrayList()
        if (cart.cartProducts.isNotEmpty()) {
            for (cartProduct in cart.cartProducts) {
                totalAmount += cartProduct.quantity
            }
            cartProductDtos = getCartProducts(cartId)
        }
        return CartDto(
            cart.cartId,
            totalPrice,
            totalAmount,
            cart.status,
            cartProductDtos
        )
    }

    override suspend fun getActiveCartByUserId(userId: String): CartDto {
        return try {
            var count: Int = cartRepository.countByUserIdAndStatus(userId, "active")
            println(count)
            var cart: Cart? = cartRepository.findCartByUserIdAndStatus(userId, "active").toList()[0]
            CartDto(
                cart?.cartId ?: "",
                cart?.totalPrice ?: 0.0,
                cart?.totalAmount ?: 0,
                cart?.status ?: "active",
                getCartProducts(cart?.cartId ?: "")
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
    override suspend fun getCartProducts(cartId: String): List<CartProductDto> {
        val cartProductDtos: MutableList<CartProductDto> = mutableListOf()
        val cartProductsOfCart = cartProductRepository.findCartProductByCartId(cartId).toList()
        for (cartProduct in cartProductsOfCart) {
            cartProductDtos.add(cartProduct.toCartProductDto())
        }
        return cartProductDtos.toList()
    }
}