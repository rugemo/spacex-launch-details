package com.russellmorris.pastlaunches.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.russellmorris.pastlaunches.data.source.LaunchDataSource
import com.russellmorris.pastlaunches.launch
import com.russellmorris.pastlaunches.launchEntity
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class LaunchRepositoryImplTest {
    private lateinit var launchRepositoryImpl: LaunchRepositoryImpl

    private val mockLaunchDataSource = mock<LaunchDataSource>()

    private val flightNumber = launch.flightNumber

    private val launchEntityItem = launchEntity
    private val launchEntityList = listOf(launchEntityItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        launchRepositoryImpl = LaunchRepositoryImpl(mockLaunchDataSource)
    }

    @Test
    fun `get launches success`() {
        whenever(mockLaunchDataSource.get()).thenReturn(Single.just(launchEntityList))
        val test = launchRepositoryImpl.get().test()
        verify(mockLaunchDataSource).get()
        test.assertValue(launchEntityList)
    }

    @Test
    fun `get launches fail`() {
        // given
        whenever(mockLaunchDataSource.getLaunchDetails(flightNumber)).thenReturn(
            Single.error(
                throwable
            )
        )

        // when
        val test = launchRepositoryImpl.getLaunchDetails(flightNumber).test()

        // then
        verify(mockLaunchDataSource).getLaunchDetails(flightNumber)
        test.assertError(throwable)
    }

}