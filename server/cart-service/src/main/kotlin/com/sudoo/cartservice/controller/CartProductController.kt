package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.UpsertCartProductDto
import com.sudoo.cartservice.service.CartProductService
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CartProductController(val cartProductService: CartProductService) : BaseController() {
    @PostMapping("/items")
    suspend fun addProductToCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody upsertCartProductDto: UpsertCartProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartProductService.addProductToActiveCart(userId, upsertCartProductDto)
    }

    @PutMapping("{cartId}/items/")
    suspend fun updateProductInCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("cartId") cartId: String,
        @RequestBody upsertCartProductDto: UpsertCartProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartProductService.updateProductInCart(cartId, upsertCartProductDto)
    }

    @DeleteMapping("{cartId}/items/{cartProductId}")
    suspend fun deleteProductInCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("cartId") cartId: String,
        @PathVariable("cartProductId") cartProductId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartProductService.deleteCartProduct(userId, cartProductId)
    }

}