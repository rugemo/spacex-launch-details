package com.russellmorris.pastlaunches.domain.repository

import com.russellmorris.pastlaunches.domain.entity.LaunchEntity
import io.reactivex.Single

interface LaunchRepository {
    fun get(): Single<List<LaunchEntity>>
    fun getLaunchDetails(flightNumber: Int): Single<LaunchEntity>
}