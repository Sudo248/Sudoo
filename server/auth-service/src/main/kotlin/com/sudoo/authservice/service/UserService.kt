package com.sudoo.authservice.service

import com.sudoo.authservice.model.Account

interface UserService {
    suspend fun createUserForAccount(account: Account): Boolean
}