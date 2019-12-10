package com.russellmorris.pastlaunches.service.api

import com.russellmorris.pastlaunches.data.source.LaunchDataSource
import com.russellmorris.pastlaunches.domain.entity.LaunchEntity
import com.russellmorris.pastlaunches.service.dao.mapToDomain
import io.reactivex.Single

class LaunchApiService constructor(
    private val api: LaunchApi
) : LaunchDataSource {
    override fun get(): Single<List<LaunchEntity>> {
        return api.getLaunches()
            .map { it.mapToDomain() }
    }

    override fun getLaunchDetails(flightNumber: Int): Single<LaunchEntity> {
        return api.getLaunchDetails(flightNumber)
            .map { it.mapToDomain() }
    }

}