package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.repository.entity.CartProduct
import com.sudoo.cartservice.service.CartProductService
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CartProductController(val cartProductService: CartProductService) : BaseController() {
    @PostMapping("/item")
    suspend fun addProductToCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @RequestBody cartProduct: CartProduct
    ): ResponseEntity<BaseResponse<*>> {
        val savedCart: CartDto = cartProductService.addProductToActiveCart(userId, cartProduct)
        return BaseResponse.ok(savedCart)
    }

    @PutMapping("/item")
    suspend fun updateProductInCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @RequestBody cartProduct: CartProduct
    ): ResponseEntity<BaseResponse<*>> = handle {
        val savedCart: CartDto = cartProductService.updateProductInCart(userId, cartProduct)
        BaseResponse.ok(savedCart)
    }

    @DeleteMapping("/item")
    suspend fun deleteProductInCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @RequestBody cartProduct: CartProduct
    ): ResponseEntity<BaseResponse<*>> = handle {
        val savedCart: CartDto = cartProductService.deleteCartProduct(userId, cartProduct)
        BaseResponse.ok(savedCart)
    }

}