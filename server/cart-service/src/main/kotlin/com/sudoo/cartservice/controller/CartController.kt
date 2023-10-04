package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.service.CartService
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
 class CartController(
        private val cartService: CartService
) {
    @PostMapping("/new/{userId}")
    fun addCart(@PathVariable userId: String): ResponseEntity<BaseResponse<*>> {
        val savedCart: CartDto = cartService.createNewCart(userId)
        return BaseResponse.ok(savedCart)
    }

    @PutMapping("/active/completed")
    fun updateStatusCart(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> {
        val updatedCart: CartDto = cartService.updateStatusCart(userId)
        return BaseResponse.ok(updatedCart)
    }

    @GetMapping("/{cartId}")
    fun getCartById(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @PathVariable cartId: String,
            @RequestParam(value = "hasRoute", required = false, defaultValue = "false") hasRoute: Boolean
    ): ResponseEntity<BaseResponse<*>> {
        val cart: CartDto = cartService.getCartById(userId, cartId, hasRoute)
        return BaseResponse.ok(cart)
    }

    @GetMapping("/active")
    fun getActiveCartByUserId(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> {
        val activeCart: CartDto = cartService.getActiveCartByUserId(userId)
        return BaseResponse.ok(activeCart)
    }

    @GetMapping("/active/count-item")
    fun getCountItemActiveCartByUserId(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> {
        val count = cartService.getCountItemActiveCart(userId)
        return BaseResponse.ok(count)
    }

    //Call from other service
    @GetMapping("/service/{cartId}")
    fun serviceGetCartById(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String,
            @PathVariable cartId: String,
            @RequestParam(value = "hasRoute", defaultValue = "false") hasRoute: Boolean
    ): CartDto {
        return cartService.getCartById(userId, cartId, hasRoute)
    }
}