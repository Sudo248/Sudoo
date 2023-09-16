package com.sudoo.authservice.repository

import com.sudoo.authservice.model.Account
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Repository
interface AccountRepository : CoroutineCrudRepository<Account, String> {

    suspend fun getAccountByEmailOrPhoneNumber(emailOrPhoneNumber: String): Optional<Account>

    suspend fun existsByEmailOrPhoneNumber(emailOrPhoneNumber: String): Boolean

    @Modifying
    @Transactional
    @Query(
        value = """
            UPDATE accounts SET accounts.is_validate=true WHERE accounts.user_id = :userId
        """
    )
    suspend fun validate(@Param("userId") userId: String)
}