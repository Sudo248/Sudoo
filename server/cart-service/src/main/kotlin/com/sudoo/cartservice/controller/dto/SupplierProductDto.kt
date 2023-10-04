package com.sudoo.cartservice.controller.dto


data class SupplierProductDto (
    val supplier: SupplierDto,
    val product: ProductDto,
    val route:RouteDto = RouteDto(),
    val amountLeft:Int = 0,
    val price:Double = 0.0,
    val soldAmount:Double = 0.0,
    val rate:Double = 0.0
)
