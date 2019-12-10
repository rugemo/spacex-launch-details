package com.russellmorris.pastlaunches.domain.usecase

import com.russellmorris.pastlaunches.domain.entity.LaunchEntity
import com.russellmorris.pastlaunches.domain.repository.LaunchRepository
import io.reactivex.Single

class LaunchUseCase constructor(private val launchRepository: LaunchRepository) {
    fun get(): Single<List<LaunchEntity>> = launchRepository.get()
    fun getLaunchDetails(flightNumber: Int): Single<LaunchEntity> =
        launchRepository.getLaunchDetails(flightNumber)
}