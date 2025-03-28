package com.dkproject.space_out.presentation.ui.sound

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.dkproject.space_out.presentation.model.SoundInfo
import com.dkproject.space_out.presentation.util.SoundUtil
import com.google.common.collect.ImmutableMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _uiState: MutableStateFlow<SoundUiState> = MutableStateFlow(SoundUiState())
    val uiState = _uiState.asStateFlow()

    fun playOrUpdateSound(soundName: String, volume: Int) {
        val updatedPlayerMap = uiState.value.playerMap.toMutableMap().apply {
            val player = get(soundName) ?: createAndPreparePlayer(soundName)
            if(volume == 0) {
                player.volume = 0f
                player.pause()
                return@apply
            }
            player.play()
            player.volume = volume / 100f
            put(soundName, player)
        }

        val updatedSoundList = uiState.value.soundList.map { sound ->
            if (sound.fileName == soundName) sound.copy(volume = volume)
            else sound
        }

        _uiState.update { currentState ->
            currentState.copy(
                playerMap = updatedPlayerMap,
                soundList = updatedSoundList
            )
        }
    }

    private fun createAndPreparePlayer(soundName: String): ExoPlayer {
        return ExoPlayer.Builder(context).build().apply {
            val uri = Uri.parse("android.resource://${context.packageName}/raw/$soundName")
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
        }
    }

    override fun onCleared() {
        super.onCleared()
        uiState.value.playerMap.values.forEach { it.release() }
    }
}


@Immutable
data class SoundUiState(
    val soundList: List<SoundInfo> = SoundUtil.getSoundList(),
    val playerMap: Map<String, ExoPlayer> = ImmutableMap.of()
)
