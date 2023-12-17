package com.sudo248.sudoo.ui.uimodel

import androidx.databinding.ObservableField
import com.sudo248.base_android.base.ItemDiff
import com.sudo248.sudoo.domain.entity.discovery.CategoryInfo
import com.sudo248.sudoo.domain.entity.discovery.SupplierInfo
import java.time.LocalDateTime


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 23:11 - 12/03/2023
 */
//data class ProductUiModel(
//    val productId: String = "",
//    val sku: String = "",
//    val name: ObservableField<String> = ObservableField(""),
//    val description: ObservableField<String> = ObservableField(""),
//    val images: ObservableField<List<String>> = ObservableField(listOf()),
//    val price: ObservableField<Double> = ObservableField(0.0),
//    val listedPrice: ObservableField<Double> = ObservableField(0.0),
//    val amount: ObservableField<Int> = ObservableField(0),
//    val soldAmount: ObservableField<Int> = ObservableField(0),
//    val rate: ObservableField<Float> = ObservableField(0.0f),
//    val discount: ObservableField<Int> = ObservableField(0),
//    val startDateDiscount: ObservableField<LocalDateTime> = ObservableField(LocalDateTime.now()),
//    val endDateDiscount: ObservableField<LocalDateTime> = ObservableField(LocalDateTime.now()),
//    var saleable: ObservableField<Boolean> = ObservableField(true),
//    val supplier: ObservableField<SupplierInfo?>? = ObservableField(),
//    val categories: ObservableField<List<CategoryInfo>?>? = ObservableField(),
//) : ItemDiff, java.io.Serializable {
//    override fun isContentTheSame(other: ItemDiff): Boolean {
//        return this == other
//    }
//
//    override fun isItemTheSame(other: ItemDiff): Boolean {
//        val productOther = other as ProductUiModel
//        return this.productId == productOther.productId &&
//                this.sku == productOther.sku &&
//                this.hashCode() == productOther.hashCode()
//    }
//}