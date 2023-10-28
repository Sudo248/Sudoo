package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.base_android.base.ItemDiff

data class UserInfo(
    val userId: String,
    val fullName: String,
    val avatar: String,
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is UserInfo && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is UserInfo && other.userId == this.userId && other.fullName == this.fullName
    }

}
