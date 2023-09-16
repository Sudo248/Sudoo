package com.sudoo.authservice.service.impl

import com.sudoo.authservice.mapper.toUserDto
import com.sudoo.authservice.model.Account
import com.sudoo.authservice.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override suspend fun createUserForAccount(account: Account): Boolean {
        val userDto = account.toUserDto()
        return true
    }
}