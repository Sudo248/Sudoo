package com.sudoo.productservice.mapper

import com.sudoo.domain.common.Constants
import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.*
import com.sudoo.productservice.model.Supplier
import com.sudoo.productservice.model.Transaction
import java.time.LocalDateTime
import java.time.ZoneId

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
        createAt = createAt ?: LocalDateTime.now(ZoneId.of(Constants.zoneId))
    ).also {
        it.isNewSupplier = supplierId.isNullOrEmpty()
    }
}

fun Transaction.toTransactionDto(): TransactionDto {
    return TransactionDto(
        transactionId = transactionId,
        ownerId = ownerId,
        amount = amount,
        description = description,
    )
}

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        transactionId = IdentifyCreator.createOrElse(transactionId),
        ownerId = ownerId,
        amount = amount,
        description = description,
    ).also {
        it.isNewTransaction = transactionId.isNullOrEmpty()
    }
}
