package com.dkproject.space_out.presentation.model

import androidx.annotation.StringRes

data class SoundInfo(
    val fileName: String,
    val volume: Int = 0,
    @StringRes val soundName: Int
)
