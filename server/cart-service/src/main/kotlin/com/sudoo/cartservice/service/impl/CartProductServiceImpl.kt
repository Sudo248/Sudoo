package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.repository.CartProductRepository
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.entity.Cart
import com.sudoo.cartservice.repository.entity.CartProduct
import com.sudoo.cartservice.repository.entity.toCartDto
import com.sudoo.cartservice.repository.entity.toCartProductDto
import com.sudoo.cartservice.service.CartProductService
import com.sudoo.cartservice.service.CartService
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CartProductServiceImpl(val cartRepository: CartRepository, val cartProductRepository: CartProductRepository, val cartService: CartService) : CartProductService {

    suspend fun getActiveCart(userId: String): Cart {
        return cartRepository.findCartByUserIdAndStatus(userId, "active").toList()[0]
    }

    override suspend fun addProductToActiveCart(userId: String, cartProduct: CartProduct): CartDto {
        val activeCart: Cart = getActiveCart(userId)
        cartProductRepository.save(cartProduct.apply { isNewCartProduct = true })
        activeCart.totalPrice += cartProduct.totalPrice
        activeCart.totalAmount += cartProduct.quantity
        activeCart.cartProducts.toMutableList().add(cartProduct)
        cartRepository.save(activeCart)

        return CartDto(
                activeCart.cartId,
                activeCart.totalPrice,
                activeCart.totalAmount,
                activeCart.status,
                cartService.getCartProducts(userId, activeCart.cartId, false)
        )
    }

    override suspend fun updateProductInCart(userId: String, cartProduct: CartProduct): CartDto {
        val cart: Cart = cartRepository.findById(cartProduct.cartId) ?: Cart()
        cart.cartProducts = cartProductRepository.findCartProductByCartId(cartProduct.cartId).toList()
        val cartProductUpdate: CartProduct = cartProductRepository.findCartProductByCartIdAndProductId(cartProduct.cartId, cartProduct.productId)
        cartProductUpdate.quantity += cartProductUpdate.quantity
        cartProductUpdate.totalPrice += cartProductUpdate.totalPrice
        cartProductRepository.save(cartProductUpdate)

        cart.totalAmount += cartProductUpdate.quantity
        cart.totalPrice += cartProductUpdate.totalPrice
        cart.cartProducts = cartProductRepository.findCartProductByCartId(cartProduct.cartId).toList()
        cartRepository.save(cart)


        return CartDto(
                cartId = cart.cartId,
                status = cart.status,
                totalPrice = cart.totalPrice,
                totalAmount = cart.totalAmount,
                cartProducts = cart.cartProducts.map { it.toCartProductDto() }
        )
    }

    override suspend fun deleteCartProduct(userId: String?, cartProduct: CartProduct): CartDto {

        val cart = cartRepository.findById(cartProduct.cartId) ?: Cart()
        val index = cartProductRepository.deleteCartProductByCartIdAndProductId(cartProduct.cartId, cartProduct.productId)
        if (index == -1) throw Exception("Not found item")
        cart.totalAmount -= cartProduct.quantity
        cart.totalPrice -= cartProduct.totalPrice
        cart.cartProducts.toMutableList().removeIf { it.cartProductId == cartProduct.cartProductId }
        cartRepository.save(cart)

        return cart.toCartDto()
    }
}