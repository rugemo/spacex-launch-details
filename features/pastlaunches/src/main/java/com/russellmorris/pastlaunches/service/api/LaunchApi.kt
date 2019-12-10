package com.russellmorris.pastlaunches.service.api

import com.russellmorris.pastlaunches.service.dao.LaunchDAO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface LaunchApi {

    @GET("launches/past")
    fun getLaunches(): Single<List<LaunchDAO>>

    @GET("launches/{flight_number}")
    fun getLaunchDetails(@Path("flight_number") lightNumber: Int): Single<LaunchDAO>

}