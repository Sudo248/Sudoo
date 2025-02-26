package com.sudo248.sudoo.domain.entity.discovery

import android.os.Parcel
import android.os.Parcelable
import com.sudo248.base_android.base.ItemDiff
import java.time.LocalDateTime

data class ProductInfo(
    val productId: String,
    val name: String,
    val sku: String,
    val images: List<String>,
    val price: Double,
    val listedPrice: Double,
    val amount: Int,
    val rate: Float,
    val discount: Int,
    val startDateDiscount: LocalDateTime?,
    val endDateDiscount: LocalDateTime?,
    val saleable: Boolean,
    val brand: String,
) : Parcelable, ItemDiff {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.createStringArrayList().orEmpty().toList(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readSerializable() as LocalDateTime? ?: LocalDateTime.now(),
        parcel.readSerializable() as LocalDateTime? ?: LocalDateTime.now(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().orEmpty()
    ) {
    }

    override fun isContentTheSame(other: ItemDiff): Boolean {
        return  other is ProductInfo && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is ProductInfo && other.productId == productId && other.sku == sku
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(name)
        parcel.writeString(sku)
        parcel.writeStringList(images)
        parcel.writeDouble(price)
        parcel.writeDouble(listedPrice)
        parcel.writeInt(amount)
        parcel.writeFloat(rate)
        parcel.writeInt(discount)
        parcel.writeByte(if (saleable) 1 else 0)
        parcel.writeString(brand)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductInfo> {
        override fun createFromParcel(parcel: Parcel): ProductInfo {
            return ProductInfo(parcel)
        }

        override fun newArray(size: Int): Array<ProductInfo?> {
            return arrayOfNulls(size)
        }
    }

}
