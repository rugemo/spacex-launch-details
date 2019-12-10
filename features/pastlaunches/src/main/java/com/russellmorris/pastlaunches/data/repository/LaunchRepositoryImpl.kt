package com.russellmorris.pastlaunches.data.repository

import com.russellmorris.pastlaunches.data.source.LaunchDataSource
import com.russellmorris.pastlaunches.domain.entity.LaunchEntity
import com.russellmorris.pastlaunches.domain.repository.LaunchRepository
import io.reactivex.Single

class LaunchRepositoryImpl constructor(
    private val launchDataSource: LaunchDataSource
) : LaunchRepository {
    override fun get(): Single<List<LaunchEntity>> {
        return launchDataSource.get()
    }

    override fun getLaunchDetails(flightNumber: Int): Single<LaunchEntity> {
        return launchDataSource.getLaunchDetails(flightNumber)
    }
}