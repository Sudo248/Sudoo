package com.sudoo.authservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "accounts")
data class Account(
    @Id
    @Column("user_id")
    var userId: String? = null,

    @Column("email_or_phone_number")
    var emailOrPhoneNumber: String,

    @Column("password")
    var password: String,

    @Column("provider")
    var provider: Provider,

    @Column("is_validated")
    var isValidated: Boolean,

    @Column("create_at")
    var createAt: LocalDateTime,

    @Column("role")
    var role: Role,
)