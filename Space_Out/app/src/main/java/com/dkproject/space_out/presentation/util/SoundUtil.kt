package com.dkproject.space_out.presentation.util

import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.model.SoundInfo

object SoundUtil {
    private const val FIRE = "fire"
    private const val WAVE = "wave"
    private const val RAIN = "rain"
    private const val WIND = "wind"
    private const val CREEK = "creek"
    private const val BIRD = "bird"
    private const val SNOW = "snow"
    private const val KEYBOARD = "keyboard"

    fun getSoundList(): List<SoundInfo> {
        return listOf(
            SoundInfo(FIRE, soundName = R.string.fire),
            SoundInfo(WAVE, soundName = R.string.wave),
            SoundInfo(RAIN, soundName = R.string.rain),
            SoundInfo(WIND, soundName = R.string.wind),
            SoundInfo(CREEK, soundName = R.string.creek),
            SoundInfo(BIRD, soundName = R.string.bird),
            SoundInfo(SNOW, soundName = R.string.snow),
            SoundInfo(KEYBOARD, soundName = R.string.keyboard),
        )
    }
}
