package com.russellmorris.pastlaunches.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.russellmorris.extensions.Resource
import com.russellmorris.extensions.parseUtcDate
import com.russellmorris.pastlaunches.R
import com.russellmorris.pastlaunches.injectFeature
import com.russellmorris.pastlaunches.ui.model.Launch
import com.russellmorris.pastlaunches.ui.viewmodel.LaunchDetailViewModel
import com.russellmorris.presentation.base.BaseFragment
import com.russellmorris.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_launch_detail.*
import org.koin.androidx.viewmodel.ext.viewModel


class LaunchDetailFragment : BaseFragment() {

    private val viewModel: LaunchDetailViewModel by viewModel()
    private val args: LaunchDetailFragmentArgs by navArgs()

    private val snackBar by lazy {
        Snackbar.make(launch_detail, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.getLaunchDetail(args.flightNumber) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        if (savedInstanceState == null) {
            viewModel.getLaunchDetail(args.flightNumber)
        }

        viewModel.launch.observe(this, Observer { update(it) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        youtube_player_view.release()
    }

    private fun update(resource: Resource<Launch>?) {
        resource?.let {
            it.data?.let {
                mission_name.text = it.missionName
                launch_date.text = it.launchDateUtc?.parseUtcDate()
                launch_details.text = it.details
                initYouTubePlayer(it.youtubeId)
            }
            it.message?.let { snackBar.show() }
        }
    }

    fun initYouTubePlayer(videoId: String?) {
        viewLifecycleOwner.lifecycle.addObserver(youtube_player_view)
        if (videoId != null) {
            youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
                }
            })
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
