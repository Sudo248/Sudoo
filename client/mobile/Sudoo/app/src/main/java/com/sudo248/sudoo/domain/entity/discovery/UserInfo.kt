package com.sudo248.sudoo.domain.entity.discovery

import android.os.Parcel
import android.os.Parcelable
import com.sudo248.base_android.base.ItemDiff

data class UserInfo(
    val userId: String,
    val fullName: String,
    val avatar: String,
) : Parcelable, ItemDiff {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    ) {
    }

    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is UserInfo && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is UserInfo && other.userId == this.userId && other.fullName == this.fullName
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(fullName)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }

}
