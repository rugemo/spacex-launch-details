package com.russellmorris.pastlaunches.service.dao

import com.russellmorris.pastlaunches.domain.entity.LaunchEntity
import com.squareup.moshi.Json

data class LaunchDAO(
    @field:Json(name = "flight_number") val flightNumber: Int,
    @field:Json(name = "mission_name") val missionName: String?,
    @field:Json(name = "launch_date_utc") val launchDateUtc: String?,
    @field:Json(name = "details") val details: String?,
    @field:Json(name = "links") val links: Links
)

data class Links(
    @field:Json(name = "youtube_id") val youtubeId: String?
)

fun LaunchDAO.mapToDomain(): LaunchEntity =
    LaunchEntity(flightNumber, missionName, launchDateUtc, details, links.youtubeId)

fun List<LaunchDAO>.mapToDomain(): List<LaunchEntity> = map { it.mapToDomain() }