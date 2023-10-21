//package com.sudoo.cartservice.service.impl
//
//import com.sudoo.cartservice.controller.dto.*
//import com.sudoo.cartservice.repository.CartProductRepository
//import com.sudoo.cartservice.repository.CartRepository
//import com.sudoo.cartservice.repository.entity.Cart
//import com.sudoo.cartservice.repository.entity.CartProduct
//import com.sudoo.cartservice.repository.entity.CartStatus
//import com.sudoo.cartservice.service.CartProductService
//import com.sudoo.cartservice.service.CartService
//import com.sudoo.cartservice.service.ProductService
//import com.sudoo.domain.exception.BadRequestException
//import com.sudoo.domain.exception.NotFoundException
//import com.sudoo.domain.utils.IdentifyCreator
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.toList
//import org.springframework.stereotype.Service
//
//@Service
//class CartProductServiceImpl(
//    val cartRepository: CartRepository,
//    val cartProductRepository: CartProductRepository,
//    val cartService: CartService,
//    val productService: ProductService,
//) : CartProductService {
//
//    private suspend fun getActiveCart(userId: String): Cart? {
//        val carts = cartRepository.findCartByUserIdAndStatus(userId, "active").toList()
//        return if (carts.isEmpty()) null else carts.first()
//    }
//
//    private suspend fun createNewCart(userId: String): Cart {
//        var cart = cartService.createCartByStatus(userId, CartStatus.ACTIVE.value)
//        return cart.toCart()
//    }
//
//    override suspend fun updateProductInCart(cartId: String, upsertCartProductDto: UpsertCartProductDto): CartDto =
//        coroutineScope {
//            if (upsertCartProductDto.cartProductId == null) throw BadRequestException("Require cart product id")
//
//            val deferreds = awaitAll(
//                async {
//                    cartRepository.findById(cartId) ?: throw NotFoundException("Not found cart $cartId")
//                },
//                async {
//                    cartProductRepository.findById(upsertCartProductDto.cartProductId) ?: throw NotFoundException(
//                        "Not found cart product ${upsertCartProductDto.cartProductId}"
//                    )
//                },
//                async {
//                    productService.getProductInfo(upsertCartProductDto.productId)
//                }
//            )
//
//            val cart = deferreds[0] as Cart
//            val cartProduct = deferreds[1] as CartProduct
//            val productInfo = deferreds[2] as ProductInfoDto
//
//            val updateQuantity = upsertCartProductDto.quantity - cartProduct.quantity
//            cartProduct.quantity += updateQuantity
//
//            cart.totalAmount += updateQuantity
//            cart.totalPrice += (updateQuantity * productInfo.price)
//
//            joinAll(
//                launch {
//                    cartProductRepository.save(cartProduct)
//                },
//                launch {
//                    cartRepository.save(cart)
//                }
//            )
//
//            CartDto(
//                cartId = cart.cartId,
//                status = cart.status,
//                totalPrice = cart.totalPrice,
//                totalAmount = cart.totalAmount,
//                cartProducts = cartService.getCartProducts(cartId)
//            )
//        }
//
//    override suspend fun deleteCartProduct(cartId: String, cartProductId: String): Boolean = coroutineScope {
//        val deferreds = awaitAll(
//            async {
//                cartRepository.findById(cartId) ?: throw NotFoundException("Not found cart $cartId")
//            },
//            async {
//                cartProductRepository.findById(cartProductId) ?: throw NotFoundException(
//                    "Not found cart product ${cartProductId}"
//                )
//            },
//        )
//
//        val cart = deferreds[0] as Cart
//        val cartProduct = deferreds[1] as CartProduct
//        val productInfo = productService.getProductInfo(cartProduct.productId)
//
//        cartProductRepository.deleteById(cartProductId)
//
//        cart.totalAmount -= cartProduct.quantity
//        cart.totalPrice -= (cartProduct.quantity * productInfo.price)
//        cartRepository.save(cart)
//        true
//    }
//
//
//    /**
//     * Nếu chưa có active cart -> tạo active cart
//     * Nếu đã có active cart -> thêm vào active cart
//     */
//
//    override suspend fun addProductToActiveCart(userId: String, upsertCartProductDto: UpsertCartProductDto): CartDto =
//        coroutineScope {
//            val activeCart = getActiveCart(userId) ?: createNewCart(userId)
//
//            //thong tin product
//            val productInfo = productService.getProductInfo(upsertCartProductDto.productId)
//
//            //thong tin cart
//            val cartProduct = cartProductRepository.findCartProductByCartIdAndProductId(
//                activeCart.cartId,
//                upsertCartProductDto.productId
//            )
//
//            if (activeCart.isNewCart || cartProduct == null) {
//                val newCartProduct = upsertCartProductDto.toCartProduct(activeCart.cartId)
//                activeCart.totalPrice += (newCartProduct.quantity * productInfo.price)
//                activeCart.totalAmount += newCartProduct.quantity
//
//                joinAll(
//                    launch {
//                        cartProductRepository.save(newCartProduct)
//                    },
//                    launch {
//                        cartRepository.save(activeCart)
//                    }
//                )
//            } else {
//                cartProduct.quantity += upsertCartProductDto.quantity
//                activeCart.totalPrice += upsertCartProductDto.quantity * productInfo.price
//                activeCart.totalAmount += upsertCartProductDto.quantity
//
//                if (cartProduct.quantity >= 0) {
//                    joinAll(
//                        launch {
//                            cartProductRepository.save(cartProduct)
//                        },
//                        launch {
//                            cartRepository.save(activeCart)
//                        }
//                    )
//                }
//
//            }
//
//            CartDto(
//                cartId = activeCart.cartId,
//                totalPrice = activeCart.totalPrice,
//                totalAmount = activeCart.totalAmount,
//                status = activeCart.status,
//                cartProducts = cartService.getCartProducts(activeCart.cartId)
//            )
//        }
//
//
//
//}