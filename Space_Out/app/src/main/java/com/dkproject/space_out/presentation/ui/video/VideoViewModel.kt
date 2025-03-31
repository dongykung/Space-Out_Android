package com.dkproject.space_out.presentation.ui.video

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    @OptIn(UnstableApi::class)
    fun preparePlayer(videoUri: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUri))
        val dataSourceFactory = DefaultDataSource.Factory(context)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}