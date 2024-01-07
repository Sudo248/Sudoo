package com.sudo248.sudoo.domain.entity.discovery

import android.os.Parcel
import android.os.Parcelable
import com.sudo248.base_android.base.ItemDiff
import java.io.Serializable
import java.time.LocalDateTime

data class Review(
    val reviewId: String,
    val rate: Float,
    val isReviewed: Boolean,
    val comment: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val userInfo: UserInfo,
    val productInfo: ProductInfo?,
) : Parcelable, Serializable, ItemDiff {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readFloat(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().orEmpty(),
        parcel.readSerializable() as LocalDateTime? ?: LocalDateTime.now(),
        parcel.readSerializable() as LocalDateTime? ?: LocalDateTime.now(),
        parcel.readParcelable<UserInfo>(UserInfo::class.java.classLoader)!! ,
        parcel.readParcelable<ProductInfo>(ProductInfo::class.java.classLoader)
    )
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is Review && this == other
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is Review &&
                this.reviewId == other.reviewId &&
                this.rate == other.rate &&
                this.isReviewed == other.isReviewed
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reviewId)
        parcel.writeFloat(rate)
        parcel.writeByte(if (isReviewed) 1 else 0)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }
}
