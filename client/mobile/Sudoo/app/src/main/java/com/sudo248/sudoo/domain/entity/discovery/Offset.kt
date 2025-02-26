package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.sudoo.domain.common.Constants

data class Offset(
    var offset: Int = 0,
    var limit: Int = Constants.DEFAULT_LIMIT
) {
    fun reset() {
        offset = 0
        limit = Constants.DEFAULT_LIMIT
    }
}
