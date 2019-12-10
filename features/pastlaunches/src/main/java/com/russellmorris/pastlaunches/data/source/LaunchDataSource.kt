package com.russellmorris.pastlaunches.data.source

import com.russellmorris.pastlaunches.domain.entity.LaunchEntity
import io.reactivex.Single

interface LaunchDataSource {
    fun get(): Single<List<LaunchEntity>>
    fun getLaunchDetails(flightNumber: Int): Single<LaunchEntity>
}