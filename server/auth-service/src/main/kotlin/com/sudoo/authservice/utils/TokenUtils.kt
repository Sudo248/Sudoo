package com.sudoo.authservice.utils

import com.sudoo.domain.common.SudooError
import com.sudoo.domain.exception.ApiException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenUtils {
    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration-token}")
    private var expirationToken: Long = 0

    @Value("\${jwt.expiration-refresh-token}")
    private var expirationRefreshToken: Long = 0

    fun generateToken(userId: String): String {
        val now = Date();
        val expireDate = Date(now.time + expirationToken)
        return Jwts.builder()
            .setId(userId)
            .setIssuedAt(now)
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun generateRefreshToken(token: String?): String {
        val now = Date()
        val expireDate = Date(now.time + expirationRefreshToken)
        return Jwts.builder()
            .setSubject(token)
            .setIssuedAt(now)
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserIdFromToken(token: String?): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.id
    }

    @Throws(ApiException::class)
    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            true
        } catch (ex: MalformedJwtException) {
            throw ApiException(HttpStatus.UNAUTHORIZED, SudooError.TOKEN_INVALID.message)
        } catch (ex: ExpiredJwtException) {
            throw ApiException(HttpStatus.UNAUTHORIZED, SudooError.TOKEN_EXPIRE.message)
        } catch (ex: UnsupportedJwtException) {
            throw ApiException(HttpStatus.UNAUTHORIZED, SudooError.TOKEN_UNSUPPORTED.message)
        } catch (ex: IllegalArgumentException) {
            throw ApiException(HttpStatus.UNAUTHORIZED, SudooError.TOKEN_CLAIM_EMPTY.message)
        }
    }
}