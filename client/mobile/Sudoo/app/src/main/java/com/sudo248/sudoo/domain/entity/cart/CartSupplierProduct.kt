package com.sudo248.sudoo.domain.entity.cart

import com.sudo248.base_android.base.ItemDiff

data class CartSupplierProduct(
    val supplierProduct: SupplierProductDetail,
    var amount: Int,
    val totalPrice: Double,
    val cartId: String
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return this == other
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        val cartSupplierProduct = other as CartSupplierProduct
        return cartSupplierProduct.supplierProduct.product.productId == supplierProduct.product.productId &&
                cartSupplierProduct.supplierProduct.supplier.supplierId == supplierProduct.supplier.supplierId &&
                cartSupplierProduct.amount == amount
    }

}