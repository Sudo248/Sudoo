package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("transactions")
data class Transaction(
    @Id
    @Column("transaction_id")
    val transactionId: String,
    @Column("owner_id")
    val ownerId: String,
    @Column("amount")
    val amount: Double,
    @Column("description")
    val description: String,
) : Persistable<String> {
    @Transient
    internal var isNewTransaction: Boolean = false

    override fun getId(): String = transactionId

    override fun isNew(): Boolean = isNewTransaction
}