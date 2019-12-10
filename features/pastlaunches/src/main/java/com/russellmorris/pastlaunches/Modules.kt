package com.russellmorris.pastlaunches

import com.russellmorris.network.createNetworkClient
import com.russellmorris.pastlaunches.data.repository.LaunchRepositoryImpl
import com.russellmorris.pastlaunches.data.source.LaunchDataSource
import com.russellmorris.pastlaunches.domain.repository.LaunchRepository
import com.russellmorris.pastlaunches.domain.usecase.LaunchUseCase
import com.russellmorris.pastlaunches.service.api.LaunchApi
import com.russellmorris.pastlaunches.service.api.LaunchApiService
import com.russellmorris.pastlaunches.ui.viewmodel.LaunchDetailViewModel
import com.russellmorris.pastlaunches.ui.viewmodel.LaunchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule
    )
}

val viewModelModule: Module = module {
    viewModel { LaunchListViewModel(launchUseCase = get()) }
    viewModel { LaunchDetailViewModel(launchUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { LaunchUseCase(launchRepository = get()) }
}

val repositoryModule: Module = module {
    single { LaunchRepositoryImpl(launchDataSource = get()) as LaunchRepository }
}

val dataSourceModule: Module = module {
    single { LaunchApiService(api = launchApi) as LaunchDataSource }
}

val networkModule: Module = module {
    single { launchApi }
}

private const val BASE_URL = "https://api.spacexdata.com/v3/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)

private val launchApi: LaunchApi = retrofit.create(LaunchApi::class.java)