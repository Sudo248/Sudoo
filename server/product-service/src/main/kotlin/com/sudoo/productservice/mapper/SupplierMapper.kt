package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.*
import com.sudoo.productservice.model.Supplier
import java.time.LocalDateTime

fun Supplier.toSupplierDto(totalProducts: Int, address: AddressDto): SupplierDto {
    return SupplierDto(
        supplierId = supplierId,
        ghnShopId = ghnShopId,
        name = name,
        avatar = avatar,
        address = address,
        contactUrl = contactUrl,
        totalProducts = totalProducts,
        rate = rate,
        createAt = createAt
    )
}

fun Supplier.toSupplierInfoDto(address: AddressDto): SupplierInfoDto {
    return SupplierInfoDto(
        supplierId = supplierId,
//        userId = userId,
        ghnShopId = ghnShopId,
        name = name,
        avatar = avatar,
        address = address,
        contactUrl = contactUrl,
        rate = rate,
    )
}

fun UpsertSupplierDto.toSupplier(userId: String, ghnShopId: Int): Supplier {
    return Supplier(
        supplierId = IdentifyCreator.createOrElse(supplierId),
        ghnShopId = ghnShopId,
        userId = userId,
        name = name,
        avatar = avatar,
        contactUrl = contactUrl.orEmpty(),
        rate = rate ?: 5.0f,
        addressId = address?.addressId.orEmpty(),
        createAt = createAt ?: LocalDateTime.now()
    ).also {
        it.isNewSupplier = supplierId.isNullOrEmpty()
    }
}
