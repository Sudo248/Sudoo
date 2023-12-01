package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.order.OrderCartProductDto
import com.sudo248.sudoo.data.dto.order.OrderDto
import com.sudo248.sudoo.data.dto.order.OrderProductInfoDto
import com.sudo248.sudoo.data.dto.order.OrderSupplierDto
import com.sudo248.sudoo.data.dto.order.UpsertOrderPromotionDto
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderCartProduct
import com.sudo248.sudoo.domain.entity.order.OrderProductInfo
import com.sudo248.sudoo.domain.entity.order.OrderSupplier
import com.sudo248.sudoo.domain.entity.order.UpsertOrderPromotion
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.entity.promotion.PromotionInfo

fun OrderDto.toOrder(): Order {
    return Order(
        orderId = orderId,
        cartId = cartId,
        payment = payment,
        promotion = promotion,
        user = user.toUser(),
        totalPrice = totalPrice,
        totalPromotionPrice = totalPromotionPrice,
        totalShipmentPrice = totalShipmentPrice,
        finalPrice = finalPrice,
        address = address,
        createdAt = createdAt,
        orderSuppliers = orderSuppliers.map {
            it.toOrderSupplier()
        }
    )
}

fun OrderSupplierDto.toOrderSupplier(): OrderSupplier {
    return OrderSupplier(
        orderSupplierId = orderSupplierId,
        supplier = supplier.toSupplierInfo(),
        promotion = promotion?.toPromotion(),
        shipment = shipment,
        status = status,
        totalPrice = totalPrice,
        expectedReceiveDateTime = expectedReceiveDateTime,
        orderCartProducts = orderCartProducts.map {
            it.toOrderCartProduct()
        }
    )
}

fun OrderCartProductDto.toOrderCartProduct(): OrderCartProduct {
    return OrderCartProduct(
        cartProductId = cartProductId,
        cartId = cartId,
        quantity = quantity,
        totalPrice = totalPrice,
        product = product.toOrderProductInfo()
    )
}

fun OrderProductInfoDto.toOrderProductInfo(): OrderProductInfo {
    return OrderProductInfo(
        productId = productId,
        supplierId = supplierId,
        name = name,
        sku = sku,
        images = images,
        price = price,
        brand = brand,
        weight = weight,
        height = height,
        length = length,
        width = width,
    )
}

fun UpsertOrderPromotionDto.toUpsertOrderPromotion(): UpsertOrderPromotion {
    return UpsertOrderPromotion(
        promotionId, orderSupplierId, totalPrice, totalPromotionPrice, finalPrice
    )
}

fun UpsertOrderPromotion.toUpsertOrderPromotionDto(): UpsertOrderPromotionDto {
    return UpsertOrderPromotionDto(promotionId, orderSupplierId)
}

fun Promotion.toPromotionInfo(): PromotionInfo {
    return PromotionInfo(
        promotionId = promotionId,
        name = name,
        value = value
    )
}