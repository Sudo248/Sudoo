package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.order.OrderCartProductDto
import com.sudo248.sudoo.data.dto.order.OrderDto
import com.sudo248.sudoo.data.dto.order.OrderProductInfoDto
import com.sudo248.sudoo.data.dto.order.OrderSupplierDto
import com.sudo248.sudoo.domain.entity.order.Order
import com.sudo248.sudoo.domain.entity.order.OrderCartProduct
import com.sudo248.sudoo.domain.entity.order.OrderProductInfo
import com.sudo248.sudoo.domain.entity.order.OrderSupplier

fun OrderDto.toOrder(): Order {
    return Order(
        orderId = orderId,
        cartId = cartId,
        payment = payment?.toPayment(),
        promotion = promotion?.toPromotion(),
        user = user.toUser(),
        status = status,
        totalPrice = totalPrice,
        totalPromotionPrice = totalPromotionPrice,
        totalShipmentPrice = totalShipmentPrice,
        finalPrice = finalPrice,
        orderSuppliers = orderSuppliers.map {
            it.toOrderSupplier()
        }
    )
}

fun OrderSupplierDto.toOrderSupplier(): OrderSupplier {
    return OrderSupplier(
        orderSupplierId = orderSupplierId,
        supplier = supplier.toSupplierInfo(),
        promotionId = promotionId,
        promotionValue = promotionValue,
        shipment = shipment,
        totalPrice = totalPrice,
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