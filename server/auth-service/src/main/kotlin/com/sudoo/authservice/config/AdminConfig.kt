package com.sudoo.authservice.config

import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.model.Role
import com.sudoo.authservice.repository.AccountRepository
import com.sudoo.authservice.service.AccountService
import com.sudoo.authservice.service.UserService
import com.sudoo.domain.utils.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.boot.context.event.ApplicationFailedEvent
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class AdminConfig (
    private val accountService: AccountService,
) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val mutex = Mutex()

    private val admins = mutableListOf(
        SignUpDto(
            emailOrPhoneNumber = "admin",
            password = "admin",
            role = Role.ADMIN
        )
    )

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent() {
        Logger.info("Init admin config")
        scope.launch {
            while (admins.size > 0) {
                admins.map { admin ->
                    launch {
                        if (accountService.registerAdmin(admin)) {
                            mutex.withLock {
                                admins.remove(admin)
                            }
                        }
                    }
                }.joinAll()
                delay(1000)
            }
        }
    }

    @EventListener(ApplicationFailedEvent::class)
    fun onApplicationFailedEvent() {
        scope.cancel()
    }
}