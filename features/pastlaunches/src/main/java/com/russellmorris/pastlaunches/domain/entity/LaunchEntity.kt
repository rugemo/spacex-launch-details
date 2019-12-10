package com.russellmorris.pastlaunches.domain.entity

data class LaunchEntity(
    val flightNumber: Int,
    val missionName: String?,
    val launchDateUtc: String?,
    val details: String?,
    val youtubId: String?
)


