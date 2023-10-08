package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.service.CartProductService
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CartProductController(val cartProductService: CartProductService): BaseController() {
    @PostMapping("/item")
    suspend fun addProductToCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @RequestBody cartProductDto: CartProductDto
    ): ResponseEntity<BaseResponse<*>> {
        val savedCart: CartDto = cartProductService.addProductToActiveCart(userId, cartProductDto)
        return BaseResponse.ok(savedCart)
    }

    @PutMapping("/{cartId}/item")
    suspend fun updateProductInCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @RequestBody cartProductDto: CartProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        val savedCart: CartDto = cartProductService.updateProductInCart(userId,cartProductDto )
        BaseResponse.ok(savedCart)
    }

    @DeleteMapping("/{cartId}/item")
    suspend fun deleteProductInCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @RequestBody cartProductDto: CartProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        val savedCart: CartDto = cartProductService.deleteCartProduct(userId,cartProductDto )
        BaseResponse.ok(savedCart)
    }

}