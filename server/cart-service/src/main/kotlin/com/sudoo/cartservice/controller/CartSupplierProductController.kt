package com.sudoo.cartservice.controller

import com.sudoo.cartservice.controller.dto.AddSupplierProductDto
import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.service.CartSupplierProductService
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CartSupplierProductController(val cartSupplierProductService: CartSupplierProductService) : BaseController() {

    @PostMapping("/item")
    suspend fun addSupplierProduct(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String?,
            @RequestBody addSupplierProductDto: AddSupplierProductDto
    ): ResponseEntity<BaseResponse<*>> {
        val savedCart: CartDto = cartSupplierProductService.addSupplierProductToCart(userId!!, addSupplierProductDto)
        return BaseResponse.ok(savedCart)
    }

    @DeleteMapping("/{cartId}/item")
    suspend fun deleteSupplierProduct(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String?,
            @PathVariable("cartId") cartId: String?,
            @RequestParam("supplierId") supplierId: String?,
            @RequestParam("productId") productId: String?
    ): ResponseEntity<BaseResponse<*>> = handle {
        val savedCart: CartDto? = cartSupplierProductService.deleteSupplierProduct(userId, cartId, supplierId, productId)
        BaseResponse.ok(savedCart)
    }


    @PutMapping("/{cartId}/item")
    suspend fun updateSupplierProduct(
            @RequestHeader(Constants.HEADER_USER_ID) userId: String?,
            @PathVariable("cartId") cartId: String?,
            @RequestBody addSupplierProductDtoList: List<AddSupplierProductDto>
    ): ResponseEntity<BaseResponse<*>> {
        val savedCart: CartDto = cartSupplierProductService.updateSupplierProductToCart(userId!!, cartId!!, addSupplierProductDtoList)
        return BaseResponse.ok(savedCart)
    }
}