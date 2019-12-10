package com.russellmorris.pastlaunches.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.russellmorris.extensions.Resource
import com.russellmorris.extensions.ResourceState
import com.russellmorris.extensions.startRefreshing
import com.russellmorris.extensions.stopRefreshing
import com.russellmorris.pastlaunches.R
import com.russellmorris.pastlaunches.injectFeature
import com.russellmorris.pastlaunches.ui.model.Launch
import com.russellmorris.pastlaunches.ui.viewmodel.LaunchListViewModel
import com.russellmorris.presentation.base.BaseFragment
import com.russellmorris.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_launches.*
import org.koin.androidx.viewmodel.ext.viewModel


class LaunchesFragment : BaseFragment() {
    private val viewModel: LaunchListViewModel by viewModel()

    private val itemClick: (Launch) -> Unit =
        {
            if (findNavController().currentDestination?.id == R.id.launchesFragment) {
                viewModel.navigate(
                    LaunchesFragmentDirections.actionLaunchesFragmentToDetailFragment(
                        it.flightNumber
                    )
                )
            }
        }
    private val adapter = LaunchesAdapter(itemClick)

    private val snackBar by lazy {
        Snackbar.make(fragment_home_swipe_to_refresh, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.get() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        if (savedInstanceState == null) {
            viewModel.get()
        }
        launches_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.launches.observe(this, Observer { updateLaunches(it) })
        fragment_home_swipe_to_refresh.setOnRefreshListener { viewModel.get() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        launches_list.adapter = null
        launches_list.layoutManager?.removeAllViews()
    }

    private fun updateLaunches(resource: Resource<List<Launch>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> fragment_home_swipe_to_refresh.startRefreshing()
                ResourceState.SUCCESS -> fragment_home_swipe_to_refresh.stopRefreshing()
                ResourceState.ERROR -> fragment_home_swipe_to_refresh.stopRefreshing()
            }
            it.data?.let { updateAdapter(it) }
            it.message?.let { snackBar.show() }
        }
    }

    private fun updateAdapter(list: List<Launch>) {
        adapter.submitList(list)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}