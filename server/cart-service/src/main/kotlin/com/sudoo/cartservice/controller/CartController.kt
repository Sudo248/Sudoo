package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductsDto
import com.sudoo.cartservice.service.CartService
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CartController(
    val cartService: CartService
) : BaseController() {
// thanh toan xog chua co update status cart
    @GetMapping("/active")
    suspend fun getActiveCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getActiveCart(userId)
    }

    @GetMapping("/active/count-item")
    suspend fun getCountItemActiveCartByUserId(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getCountItemActiveCart(userId)
    }

    @GetMapping("/{cartId}")
    suspend fun getCartById(
        @PathVariable cartId: String,
        @RequestParam(value = "orderInfo", required = false, defaultValue = "false") orderInfo: Boolean
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getCartById(cartId)
    }


    @PostMapping("/processing")
    suspend fun createProcessingCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody cartProductsDto: CartProductsDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.createProcessingCart(userId, cartProductsDto.cartProducts)
    }

    @GetMapping("/processing")
        suspend fun getProcessingCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getProcessingCart(userId)
    }

    @DeleteMapping("/processing")
    suspend fun deleteProcessingCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.deleteProcessingCart(userId)
    }

    @PostMapping("/checkout")
    suspend fun checkoutProcessingCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.checkoutProcessingCart(userId)
    }

    //Call from other service
    @GetMapping("/internal/{cartId}")
    suspend fun serviceGetCartById(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable cartId: String,
        @RequestParam(value = "hasRoute", defaultValue = "false") hasRoute: Boolean
    ): CartDto {
        return cartService.getCartById(cartId)
    }
}