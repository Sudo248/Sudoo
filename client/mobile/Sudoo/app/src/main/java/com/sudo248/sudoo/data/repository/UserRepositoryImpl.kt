package com.sudo248.sudoo.data.repository

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.user.UserService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toAddressSuggestion
import com.sudo248.sudoo.data.mapper.toUser
import com.sudo248.sudoo.data.mapper.toUserDto
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.user.AddressSuggestion
import com.sudo248.sudoo.domain.entity.user.Location
import com.sudo248.sudoo.domain.entity.user.User
import com.sudo248.sudoo.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val locationService: FusedLocationProviderClient,
    private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun getUserInfo(): DataState<User, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(userService.getUser())
        if (response.isSuccess) {
            response.data().toUser()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun updateUser(user: User, isUpdate: Boolean): DataState<User, Exception> = stateOn(ioDispatcher){
        val userDto = user.toUserDto()
        val response = handleResponse(userService.updateUser(userDto))
        if (response.isSuccess) {
            response.data().toUser()
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getAddressSuggestionProvince(): DataState<List<AddressSuggestion>, Exception> = stateOn(ioDispatcher) {
        val response = handleResponse(userService.getAddressSuggestionProvince())
        if (response.isSuccess) {
            response.data().map { it.toAddressSuggestion() }
        } else {
            throw response.error().errorBody()
        }

    }

    override suspend fun getAddressSuggestionDistrict(provinceId: Int): DataState<List<AddressSuggestion>, Exception> = stateOn(ioDispatcher){
        val response = handleResponse(userService.getAddressSuggestionDistrict(provinceId))

        if (response.isSuccess) {
            response.data().map { it.toAddressSuggestion() }
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getAddressSuggestionWard(districtId: Int): DataState<List<AddressSuggestion>, Exception> = stateOn(ioDispatcher){
        val response = handleResponse(userService.getAddressSuggestionWard(districtId))

        if (response.isSuccess) {
            response.data().map { it.toAddressSuggestion() }
        } else {
            throw response.error().errorBody()
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation(): Location = suspendCoroutine { continuation ->
//        locationService.lastLocation.addOnCompleteListener {
//            it.result.run {
//                continuation.resume(Location(longitude, latitude))
//            }
//        }

        locationService.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener {
                it.run {
                    Constants.location = "$longitude,$latitude"
                    continuation.resume(Location(longitude, latitude))
                }
            }
            .addOnFailureListener {
                throw it
            }
    }
}