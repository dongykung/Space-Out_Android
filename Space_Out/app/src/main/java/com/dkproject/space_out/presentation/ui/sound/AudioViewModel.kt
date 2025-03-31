package com.dkproject.space_out.presentation.ui.sound

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.dkproject.space_out.R
import com.dkproject.space_out.data.local.ThemeRepository
import com.dkproject.space_out.presentation.model.SoundInfo
import com.dkproject.space_out.presentation.model.ThemeData
import com.dkproject.space_out.presentation.model.ThemeInfo
import com.dkproject.space_out.presentation.util.SoundUtil
import com.dkproject.space_out.presentation.util.ThemeUtil
import com.google.common.collect.ImmutableMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val themeRepository: ThemeRepository,
    ) : ViewModel() {

    val themeData: StateFlow<ThemeData> = themeRepository.getThemeSetting()
        .map {
            ThemeData(
                primaryColor = Color(it.primaryColor),
                sliderEmptyColor = Color(it.sliderEmptyColor),
                sliderFillColor = Color(it.sliderFillColor),
                themeColorList = it.themeColorListList.map { color -> Color(color) }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeUtil.Monochrome
        )
    private val _uiState: MutableStateFlow<SoundUiState> = MutableStateFlow(SoundUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<SoundUiEvent> = MutableSharedFlow<SoundUiEvent>()
    val uiEvent: SharedFlow<SoundUiEvent> = _uiEvent.asSharedFlow()

    fun playOrUpdateSound(soundName: String, volume: Int) {
        val updatedPlayerMap = uiState.value.playerMap.toMutableMap().apply {
            val player = get(soundName) ?: createAndPreparePlayer(soundName)
            if (volume == 0) {
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

    fun updateTheme(theme: ThemeInfo) {
        viewModelScope.launch {
            themeRepository.updateThemeSetting(themeData = theme.theme).fold(
                onSuccess = {
                    _uiEvent.emit(SoundUiEvent.ShowToastMessage(context.getString(R.string.themechange, context.getString(theme.themeName))))
                },
                onFailure = {
                    _uiEvent.emit(SoundUiEvent.ShowToastMessage(context.getString(R.string.themechangefail)))
                }
            )
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

sealed class SoundUiEvent {
    data class ShowToastMessage(val message: String) : SoundUiEvent()
}
