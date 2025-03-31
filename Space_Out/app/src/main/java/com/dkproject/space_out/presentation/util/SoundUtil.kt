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
    private const val Scissor = "scissor"
    private const val Heart = "heartbeat"

    fun getSoundList(): List<SoundInfo> {
        return listOf(
            SoundInfo(FIRE, soundName = R.string.fire, offImage = R.drawable.fireoff, onImage = R.drawable.fireon),
            SoundInfo(WAVE, soundName = R.string.wave,offImage = R.drawable.waveoff, onImage = R.drawable.waveon),
            SoundInfo(RAIN, soundName = R.string.rain,offImage = R.drawable.rainoff, onImage = R.drawable.rainon),
            SoundInfo(WIND, soundName = R.string.wind,offImage = R.drawable.windoff, onImage = R.drawable.windon),
            SoundInfo(CREEK, soundName = R.string.creek,offImage = R.drawable.creekoff, onImage = R.drawable.creekon),
            SoundInfo(BIRD, soundName = R.string.bird,offImage = R.drawable.birdoff, onImage = R.drawable.birdon),
            SoundInfo(SNOW, soundName = R.string.snow,offImage = R.drawable.snowoff, onImage = R.drawable.snowon),
            SoundInfo(KEYBOARD, soundName = R.string.keyboard,offImage = R.drawable.keyboardoff, onImage = R.drawable.keyboardon),
            SoundInfo(Scissor, soundName = R.string.scissor, offImage = R.drawable.scissoroff, onImage = R.drawable.scissoron),
            SoundInfo(Heart, soundName = R.string.heart, offImage = R.drawable.heartoff, onImage = R.drawable.hearton)
        )
    }
}
