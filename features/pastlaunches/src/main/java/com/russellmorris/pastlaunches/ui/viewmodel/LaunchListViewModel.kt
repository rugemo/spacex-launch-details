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

class LaunchListViewModel constructor(private val launchUseCase: LaunchUseCase) : BaseViewModel() {

    val launches = MutableLiveData<Resource<List<Launch>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        get()
    }

    fun get() =
        compositeDisposable.add(launchUseCase.get()
            .doOnSubscribe { launches.setLoading() }
            .subscribeOn(Schedulers.io())
            .map { it.mapToPresentation() }
            .subscribe({ launches.setSuccess(it) }, { launches.setError(it.message) })
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}