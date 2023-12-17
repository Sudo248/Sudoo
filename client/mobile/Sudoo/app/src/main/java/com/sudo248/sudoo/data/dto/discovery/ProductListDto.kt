package com.sudo248.sudoo.data.dto.discovery

data class ProductListDto(
    val products: List<ProductInfoDto>,
    val pagination: PaginationDto,
)
