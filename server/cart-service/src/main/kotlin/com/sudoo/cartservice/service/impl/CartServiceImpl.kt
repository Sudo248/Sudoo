package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.controller.dto.toCart
import com.sudoo.cartservice.repository.CartProductRepository
//import com.sudoo.cartservice.service.ProductService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartStatus
import com.sudoo.cartservice.repository.entity.toCartDto
import com.sudoo.cartservice.repository.entity.toCartProductDto
import com.sudoo.cartservice.service.CartService
import com.sudoo.cartservice.service.ProductService
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.domain.utils.IdentifyCreator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
    val cartRepository: CartRepository,
    val cartProductRepository: CartProductRepository,
    val productService: ProductService
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
            userId = cart.userId,
            cartId = cart.cartId,
            totalPrice = totalPrice,
            totalAmount = totalAmount,
            status = cart.status,
            cartProducts = cartProductDtos
        )
    }

    override suspend fun getActiveCart(userId: String): CartDto {
        return try {
            val activeCarts = cartRepository.findCartByUserIdAndStatus(userId, "active").toList()

            val cart: Cart =
                if (activeCarts.isNotEmpty()) activeCarts[0] else createCartByStatus(userId, "active").toCart()

            CartDto(
                userId = cart.userId,
                cartId = cart.cartId,
                totalPrice = cart.totalPrice,
                totalAmount = cart.totalAmount,
                status = cart.status,
                cartProducts = getCartProducts(cart.cartId)
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
            cartProductDtos.add(cartProduct.toCartProductDto( productService.getProductInfo(cartProduct.productId)))
        }
        return cartProductDtos.toList()
    }


}