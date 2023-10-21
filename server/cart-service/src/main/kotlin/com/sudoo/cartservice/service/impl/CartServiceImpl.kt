package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.*
import com.sudoo.cartservice.repository.CartProductRepository
//import com.sudoo.cartservice.service.ProductService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.entity.*
import com.sudoo.cartservice.service.CartService
import com.sudoo.cartservice.service.ProductService
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.domain.utils.IdentifyCreator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
    val cartRepository: CartRepository,
    val cartProductRepository: CartProductRepository,
    val productService: ProductService
) : CartService {

    /**
     * Lấy active cart, mỗi user chỉ có 1 active cart
     */
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

    override suspend fun createCartByStatus(userId: String, status: String): CartDto {
        val cart = Cart(
            cartId = IdentifyCreator.create(),
            userId = userId,
            totalAmount = 0,
            totalPrice = 0.0,
            status = when (status) {
                CartStatus.ACTIVE.value -> CartStatus.ACTIVE.value
                CartStatus.PROCESSING.value -> CartStatus.PROCESSING.value
                else -> CartStatus.COMPLETED.value
            },
        ).apply { isNewCart = true }
        cartRepository.save(cart)
        return getCartById(userId = userId, cartId = cart.cartId, false)
    }

    /**
     * Lấy danh sách product trong 1 cart
     */
    override suspend fun getCartProducts(cartId: String): List<CartProductDto> {
        val cartProducts: MutableList<CartProductDto> = mutableListOf()
        val cartProductsOfCart = cartProductRepository.findCartProductByCartId(cartId).toList()
        for (cartProduct in cartProductsOfCart) {
            cartProducts.add(cartProduct.toCartProductDto(productService.getProductInfo(cartProduct.productId)))
        }
        return cartProducts.toList()
    }

    /**
     * Lấy chính xác 1 cart của user
     */
    override suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto {
        val cart = cartRepository.findById(cartId) ?: throw NotFoundException("Not found cart $cartId")
        val totalPrice = 0.0
        var totalAmount = 0
        var cartProducts: List<CartProductDto> = ArrayList()
        if (cart.cartProducts.isNotEmpty()) {
            for (cartProduct in cart.cartProducts) {
                totalAmount += cartProduct.quantity
            }
            cartProducts = getCartProducts(cartId)
        }
        return CartDto(
            userId = cart.userId,
            cartId = cart.cartId,
            totalPrice = totalPrice,
            totalAmount = totalAmount,
            status = cart.status,
            cartProducts = cartProducts
        )
    }

    /**
     * Tạo cart processing phục vụ order
     */
    override suspend fun createProcessingCart(userId: String, cartProducts: List<CartProductDto>): CartDto {
        val processingCart = createCartByStatus(userId, CartStatus.PROCESSING.value)
        val cartProductsOfProcessingCart = mutableListOf<CartProductDto>()
        for (cartProduct in cartProducts) {
            val cartProductOfProcessingCart = CartProductDto(
                cartProductId = IdentifyCreator.create(),
                cartId = processingCart.cartId,
                totalPrice = cartProduct.totalPrice,
                quantity = cartProduct.quantity,
                product = cartProduct.product
            )
            cartProductRepository.save(cartProductOfProcessingCart.toCartProduct().apply { isNewCartProduct = true })
            cartProductsOfProcessingCart.add(
                cartProductOfProcessingCart
            )
            processingCart.totalPrice += (cartProduct.product?.price ?: 0.0f) * cartProduct.quantity
        }

        processingCart.totalAmount = cartProducts.size
        processingCart.cartProducts = cartProductsOfProcessingCart
        cartRepository.save(processingCart.toCart())

        return processingCart
    }

    override suspend fun deleteProcessingCart(userId: String): Boolean {
        val processingCart = try {
            cartRepository.findCartByUserIdAndStatus(userId, CartStatus.PROCESSING.value).toList()[0]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return if (processingCart != null) {
            cartRepository.deleteById(processingCart.cartId)
            true
        } else {
            true
        }
    }

    override suspend fun checkoutProcessingCart(userId: String) {
        val processingCart = try {
            cartRepository.findCartByUserIdAndStatus(userId, CartStatus.ACTIVE.value).toList()[0]
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

        val productIdsInProcessingCart =
            cartProductRepository.findCartProductByCartId(processingCart.cartId).toList().map { it.productId }


        val activeCart = try {
            cartRepository.findCartByUserIdAndStatus(userId, CartStatus.ACTIVE.value).toList()[0]
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

        for (productId in productIdsInProcessingCart) {
            val cartProductInActiveCart =
                cartProductRepository.findCartProductByCartIdAndProductId(activeCart.cartId, productId)

            cartProductInActiveCart?.cartProductId?.let { cartProductRepository.deleteById(it) }
        }


    }


    //------------------------------------------------------------------------------------------------------------------

    /**
     * Thêm, bớt mặt hàng vào active cart
     */
    override suspend fun updateProductInActiveCart(
        userId: String,
        upsertCartProductDto: UpsertCartProductDto
    ): CartDto =
        coroutineScope {
//            if (upsertCartProductDto.cartProductId == null) throw BadRequestException("Require cart product id")
            val activeCart = getActiveCart(userId)
            val deferred = awaitAll(
                async {
                    cartProductRepository.findCartProductByCartIdAndProductId(
                        activeCart.cartId,
                        upsertCartProductDto.productId
                    )
                },
                async {
                    try {
                        productService.getProductInfo(upsertCartProductDto.productId)
                    } catch (e: Exception) {
                        throw NotFoundException("Not found product ${upsertCartProductDto.productId}")
                    }
                }
            )

            val cartProduct = deferred[0] as CartProduct?
            val productInfo = deferred[1] as ProductInfoDto

            if (cartProduct == null) {
                val newCartProduct = upsertCartProductDto.toCartProduct(activeCart.cartId)
                activeCart.totalPrice += (newCartProduct.quantity * productInfo.price)
                activeCart.totalAmount += newCartProduct.quantity

                joinAll(
                    launch {
                        cartProductRepository.save(newCartProduct)
                    },
                    launch {
                        cartRepository.save(activeCart.toCart())
                    }
                )
            } else {
                cartProduct.quantity += upsertCartProductDto.quantity
                activeCart.totalPrice += upsertCartProductDto.quantity * productInfo.price
                activeCart.totalAmount += upsertCartProductDto.quantity

                if (cartProduct.quantity >= 0) {
                    joinAll(
                        launch {
                            cartProductRepository.save(cartProduct)
                        },
                        launch {
                            cartRepository.save(activeCart.toCart())
                        }
                    )
                }

            }

            CartDto(
                cartId = activeCart.cartId,
                totalPrice = activeCart.totalPrice,
                totalAmount = activeCart.totalAmount,
                status = activeCart.status,
                cartProducts = getCartProducts(activeCart.cartId)
            )
        }

    override suspend fun deleteCartProduct(cartId: String, cartProductId: String): Boolean = coroutineScope {
        val deferred = awaitAll(
            async {
                cartRepository.findById(cartId) ?: throw NotFoundException("Not found cart $cartId")
            },
            async {
                cartProductRepository.findById(cartProductId) ?: throw NotFoundException(
                    "Not found cart product $cartProductId"
                )
            },
        )

        val cart = deferred[0] as Cart
        val cartProduct = deferred[1] as CartProduct
        val productInfo = productService.getProductInfo(cartProduct.productId)

        cartProductRepository.deleteById(cartProductId)

        cart.totalAmount -= cartProduct.quantity
        cart.totalPrice -= (cartProduct.quantity * productInfo.price)
        cartRepository.save(cart)
        true
    }

    //------------------------------------------------------------------------------------------------------------------


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

    override suspend fun getCountItemActiveCart(userId: String): Int {
        return try {
            cartRepository.countByUserIdAndStatus(userId, "active")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            0
        }
    }


}