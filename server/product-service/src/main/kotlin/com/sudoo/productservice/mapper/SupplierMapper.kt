package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.Location
import com.sudoo.productservice.dto.SupplierDto
import com.sudoo.productservice.dto.SupplierInfoDto
import com.sudoo.productservice.model.Supplier
import java.time.LocalDateTime

fun Supplier.toSupplierDto(totalProducts: Int): SupplierDto {
    return SupplierDto(
        supplierId = supplierId,
        name = name,
        avatar = avatar,
        brand = brand,
        location = Location(longitude = longitude, latitude = latitude),
        locationName = locationName,
        contactUrl = contactUrl,
        totalProducts = totalProducts,
        rate = rate,
        createAt = createAt
    )
}

fun Supplier.toSupplierInfoDto(): SupplierInfoDto {
    return SupplierInfoDto(
        supplierId = supplierId,
        userId = userId,
        name = name,
        avatar = avatar,
        brand = brand,
        locationName = locationName,
        contactUrl = contactUrl,
        rate = rate,
    )
}

fun SupplierDto.toSupplier(userId: String): Supplier {
    return Supplier(
        supplierId = IdentifyCreator.createOrElse(supplierId),
        userId = userId,
        name = name,
        avatar = avatar,
        brand = brand,
        longitude = location.longitude,
        latitude = location.latitude,
        locationName = locationName,
        contactUrl = contactUrl,
        rate = rate ?: 5.0f,
        createAt = createAt ?: LocalDateTime.now()
    ).also {
        it.isNewSupplier = supplierId.isNullOrEmpty()
    }
}
