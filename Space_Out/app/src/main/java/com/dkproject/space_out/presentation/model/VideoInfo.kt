package com.dkproject.space_out.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.UUID

data class VideoInfo(
    val id: UUID = UUID.randomUUID(),
    @DrawableRes val thumbnail: Int,
    @StringRes val videoName: Int,
    val videoPath: String
)