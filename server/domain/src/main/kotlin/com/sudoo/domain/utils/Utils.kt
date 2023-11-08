package com.sudoo.domain.utils

object Utils {
    fun getNexOffset(offset: Int?, count: Int): Int {
        if (offset == null || offset == 0) return 0
        if (count == 0) return offset
        return offset + count + 1
    }

    fun getUserNameFromEmail(email: String): String {
        return email.split("@").run {
            if (isEmpty()) ""
            else first()
        }
    }
}