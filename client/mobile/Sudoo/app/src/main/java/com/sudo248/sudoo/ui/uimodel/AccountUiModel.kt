package com.sudo248.sudoo.ui.uimodel

import androidx.databinding.ObservableField


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 10:52 - 12/03/2023
 */
data class AccountUiModel(
    val phoneNumber: ObservableField<String> = ObservableField(""),
    val password: ObservableField<String> = ObservableField(""),
)