package com.russellmorris.pastlaunches.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.russellmorris.extensions.Resource
import com.russellmorris.extensions.setError
import com.russellmorris.extensions.setLoading
import com.russellmorris.extensions.setSuccess
import com.russellmorris.pastlaunches.domain.usecase.LaunchUseCase
import com.russellmorris.pastlaunches.ui.model.Launch
import com.russellmorris.pastlaunches.ui.model.mapToPresentation
import com.russellmorris.presentation.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LaunchDetailViewModel constructor(private val launchUseCase: LaunchUseCase) :
    BaseViewModel() {

    val launch = MutableLiveData<Resource<Launch>>()
    private val compositeDisposable = CompositeDisposable()

    fun getLaunchDetail(flightNumber: Int) =
        compositeDisposable.add(launchUseCase.getLaunchDetails(flightNumber)
            .doOnSubscribe { launch.setLoading() }
            .subscribeOn(Schedulers.io())
            .map { it.mapToPresentation() }
            .subscribe({ launch.setSuccess(it) }, { launch.setError(it.message) })
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}