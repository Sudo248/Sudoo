package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.controller.dto.toCartProduct
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
import java.lang.Exception

@Service
class CartProductServiceImpl(val cartRepository: CartRepository, val cartProductRepository: CartProductRepository, val cartService: CartService) : CartProductService {

    suspend fun getActiveCart(userId: String): Cart {
        return cartRepository.findCartByUserIdAndStatus(userId, "active")
    }
    override suspend fun addProductToActiveCart(userId: String, cartProductDto: CartProductDto): CartDto {
        val activeCart: Cart = getActiveCart(userId)
        cartProductRepository.save(cartProductDto.toCartProduct())
        activeCart.totalPrice += cartProductDto.totalPrice
        activeCart.totalAmount += cartProductDto.quantity
        activeCart.cartProducts.toMutableList().add(cartProductDto.toCartProduct())
        cartRepository.save(activeCart)

        return CartDto(
                activeCart.cartId,
                activeCart.totalPrice,
                activeCart.totalAmount,
                activeCart.status,
                cartService.getCartProducts(userId, activeCart.cartId, false)
        )
    }

    /**
     * Cập nhật product trong cart:
     * - Cập nhật Cart
     * - Cập nhật CartProduct
     * - Trả về CartDto
     * Cộng thêm phần thay đổi vào số hiện tại
     */
    override suspend fun updateProductInCart(cartId: String, cartProductDto: CartProductDto): CartDto {
        val cart: Cart = cartRepository.findById(cartId) ?: Cart()
        val cartProduct: CartProduct = cartProductRepository.findCartProductByCartIdAndProductId(cartId, cartProductDto.productId)
        cartProduct.quantity += cartProductDto.quantity
        cartProduct.totalPrice += cartProductDto.totalPrice
        cartProductRepository.save(cartProduct)

        cart.totalAmount += cartProductDto.quantity
        cart.totalPrice += cartProductDto.totalPrice
        val cartProductInCart = cart.cartProducts.findLast { it.cartProductId == cartProductDto.cartProductId }?: CartProduct()
        cartProductInCart.totalPrice = cartProduct.totalPrice
        cartProductInCart.quantity = cartProduct.quantity
        cartRepository.save(cart)


        return CartDto(
                cartId= cart.cartId,
                status = cart.status,
                totalPrice = cart.totalPrice,
                totalAmount = cart.totalAmount,
                cartProducts = cartProductRepository.findCartProductByCartId(cartId).toList().map { it.toCartProductDto() }
        )
    }

    override suspend fun deleteCartProduct(userId: String?, cartProductDto: CartProductDto): CartDto {

        val cart = cartRepository.findById(cartProductDto.cartId)?:Cart()
        val index = cartProductRepository.deleteCartProductByCartIdAndProductId(cartProductDto.cartId,cartProductDto.productId)
        if(index==-1) throw Exception("Not found item")
        cart.totalAmount -= cartProductDto.quantity
        cart.totalPrice -= cartProductDto.totalPrice
        cart.cartProducts.toMutableList().removeIf { it.cartProductId == cartProductDto.cartProductId }
        cartRepository.save(cart)

        return cart.toCartDto()
    }
}