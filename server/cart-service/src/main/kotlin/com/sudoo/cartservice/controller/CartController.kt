package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartProductDto
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

    @GetMapping("/active")
    suspend fun getActiveCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getActiveCart(userId)
    }

    @GetMapping("/{cartId}")
    suspend fun getCartById(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable cartId: String,
        @RequestParam(value = "hasRoute", required = false, defaultValue = "false") hasRoute: Boolean
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getCartById(userId, cartId, hasRoute)
    }

    @PostMapping("/processing")
    suspend fun createProcessingCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody cartProductsDto: CartProductsDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.createProcessingCart(userId, cartProductsDto.cartProducts)
    }

    @DeleteMapping("/processing")
    suspend fun deleteProcessingCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.deleteProcessingCart(userId)
    }


    //------------------------------------------------------------------------------------------------------------------
    @PostMapping("/create")
    suspend fun createCartByStatus(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable("status") status: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.createCartByStatus(userId, status)
    }

    @PostMapping("/")
    suspend fun createNewCart(@RequestHeader(Constants.HEADER_USER_ID) userId: String): ResponseEntity<BaseResponse<*>> =
        handle {
            cartService.createNewCart(userId)
        }

    @PutMapping("/active/completed")
    suspend fun updateStatusCart(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.updateStatusCart(userId)
    }


    @GetMapping("/active/count-item")
    suspend fun getCountItemActiveCartByUserId(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        cartService.getCountItemActiveCart(userId)
    }

    //Call from other service
    @GetMapping("/internal/{cartId}")
    suspend fun serviceGetCartById(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @PathVariable cartId: String,
        @RequestParam(value = "hasRoute", defaultValue = "false") hasRoute: Boolean
    ): CartDto {
        return cartService.getCartById(userId, cartId, hasRoute)
    }
}