package com.sudo248.sudoo.domain.entity.chat

import com.sudo248.base_android.base.ItemDiff
import java.util.*

data class Chat (
    val sender: UserChat,
    val receiver: UserChat,
    val content: String,
    val timestamp: Long
) : ItemDiff {
    override fun isContentTheSame(other: ItemDiff): Boolean {
        return other is Chat && other == this
    }

    override fun isItemTheSame(other: ItemDiff): Boolean {
        return other is Chat && other.timestamp == timestamp
    }

}
