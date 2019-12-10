package com.russellmorris.pastlaunches.ui.model

import com.russellmorris.pastlaunches.domain.entity.LaunchEntity

data class Launch(
    val flightNumber: Int,
    val missionName: String?,
    val launchDateUtc: String?,
    val details: String?,
    val youtubeId: String?
)

fun LaunchEntity.mapToPresentation(): Launch =
    Launch(flightNumber, missionName, launchDateUtc, details, youtubId)

fun List<LaunchEntity>.mapToPresentation(): List<Launch> =
    map { it.mapToPresentation() }