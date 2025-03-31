package com.dkproject.space_out.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class ThemeData(
    val primaryColor: Color,
    val sliderEmptyColor: Color,
    val sliderFillColor: Color,
    val themeColorList: List<Color>
)

data class ThemeInfo(
    @StringRes val themeName: Int,
    @DrawableRes val themeImage: Int,
    val theme: ThemeData
)