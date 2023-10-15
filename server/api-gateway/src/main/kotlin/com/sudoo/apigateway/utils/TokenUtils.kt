package com.sudoo.apigateway.utils

import com.sudoo.domain.common.SudooError
import com.sudoo.domain.exception.ApiException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TokenUtils {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    fun getUserIdFromToken(token: String): String {
        val claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        return claims.id
    }

    fun validateToken(token: String): Boolean {
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