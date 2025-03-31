package com.dkproject.space_out.presentation.util

import androidx.compose.ui.graphics.Color
import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.model.ThemeData
import com.dkproject.space_out.presentation.model.ThemeInfo
import com.dkproject.space_out.presentation.theme.CoralReef_EmptySlider
import com.dkproject.space_out.presentation.theme.CoralReef_FillSlider
import com.dkproject.space_out.presentation.theme.CoralReef_GradientEnd
import com.dkproject.space_out.presentation.theme.CoralReef_GradientMiddle
import com.dkproject.space_out.presentation.theme.CoralReef_GradientStart
import com.dkproject.space_out.presentation.theme.CoralReef_Primary
import com.dkproject.space_out.presentation.theme.DeepOcean_EmptySlider
import com.dkproject.space_out.presentation.theme.DeepOcean_FillSlider
import com.dkproject.space_out.presentation.theme.DeepOcean_GradientEnd
import com.dkproject.space_out.presentation.theme.DeepOcean_GradientStart
import com.dkproject.space_out.presentation.theme.DeepOcean_Primary
import com.dkproject.space_out.presentation.theme.Forest_EmptySlider
import com.dkproject.space_out.presentation.theme.Forest_FillSlider
import com.dkproject.space_out.presentation.theme.Forest_GradientEnd
import com.dkproject.space_out.presentation.theme.Forest_GradientStart
import com.dkproject.space_out.presentation.theme.Forest_Primary
import com.dkproject.space_out.presentation.theme.GalaxyNight_EmptySlider
import com.dkproject.space_out.presentation.theme.GalaxyNight_FillSlider
import com.dkproject.space_out.presentation.theme.GalaxyNight_GradientEnd
import com.dkproject.space_out.presentation.theme.GalaxyNight_GradientStart
import com.dkproject.space_out.presentation.theme.GalaxyNight_Primary
import com.dkproject.space_out.presentation.theme.Midnight_EmptySlider
import com.dkproject.space_out.presentation.theme.Midnight_FillSlider
import com.dkproject.space_out.presentation.theme.Midnight_GradientEnd
import com.dkproject.space_out.presentation.theme.Midnight_GradientStart
import com.dkproject.space_out.presentation.theme.Midnight_Primary
import com.dkproject.space_out.presentation.theme.Monochrome_EmptySlider
import com.dkproject.space_out.presentation.theme.Monochrome_FillSlider
import com.dkproject.space_out.presentation.theme.Monochrome_GradientEnd
import com.dkproject.space_out.presentation.theme.Monochrome_GradientMiddle
import com.dkproject.space_out.presentation.theme.Monochrome_GradientStart
import com.dkproject.space_out.presentation.theme.Monochrome_Primary
import com.dkproject.space_out.presentation.theme.OceanCalm_EmptySlider
import com.dkproject.space_out.presentation.theme.OceanCalm_FillSlider
import com.dkproject.space_out.presentation.theme.OceanCalm_GradientEnd
import com.dkproject.space_out.presentation.theme.OceanCalm_GradientStart
import com.dkproject.space_out.presentation.theme.OceanCalm_Primary
import com.dkproject.space_out.presentation.theme.Pastel_EmptySlider
import com.dkproject.space_out.presentation.theme.Pastel_FillSlider
import com.dkproject.space_out.presentation.theme.Pastel_GradientEnd
import com.dkproject.space_out.presentation.theme.Pastel_GradientStart
import com.dkproject.space_out.presentation.theme.Pastel_Primary

object ThemeUtil {
    val Midnight = ThemeData(
        primaryColor = Midnight_Primary,
        sliderEmptyColor = Midnight_EmptySlider,
        sliderFillColor = Midnight_FillSlider,
        themeColorList = listOf(Midnight_GradientStart, Midnight_GradientEnd)
    )

    val OceanCalm = ThemeData(
        primaryColor = OceanCalm_Primary,
        sliderEmptyColor = OceanCalm_EmptySlider,
        sliderFillColor = OceanCalm_FillSlider,
        themeColorList = listOf(OceanCalm_GradientStart, OceanCalm_GradientEnd)
    )

    val CoralReef = ThemeData(
        primaryColor = CoralReef_Primary,
        sliderEmptyColor = CoralReef_EmptySlider,
        sliderFillColor = CoralReef_FillSlider,
        themeColorList = listOf(CoralReef_GradientStart, CoralReef_GradientMiddle, CoralReef_GradientEnd)
    )

    val Monochrome = ThemeData(
        primaryColor = Monochrome_Primary,
        sliderEmptyColor = Monochrome_EmptySlider,
        sliderFillColor = Monochrome_FillSlider,
        themeColorList = listOf(Monochrome_GradientStart, Monochrome_GradientMiddle, Monochrome_GradientEnd)
    )

    val Pastel = ThemeData(
        primaryColor = Pastel_Primary,
        sliderEmptyColor = Pastel_EmptySlider,
        sliderFillColor = Pastel_FillSlider,
        themeColorList = listOf(Pastel_GradientStart, Pastel_GradientEnd)
    )

    val Forest = ThemeData(
        primaryColor = Forest_Primary,
        sliderEmptyColor = Forest_EmptySlider,
        sliderFillColor = Forest_FillSlider,
        themeColorList = listOf(Forest_GradientStart, Forest_GradientEnd)
    )

    val GalaxyNight = ThemeData(
        primaryColor = GalaxyNight_Primary,
        sliderEmptyColor = GalaxyNight_EmptySlider,
        sliderFillColor = GalaxyNight_FillSlider,
        themeColorList = listOf(GalaxyNight_GradientStart, GalaxyNight_GradientEnd)
    )

    val DeepOcean = ThemeData(
        primaryColor = DeepOcean_Primary,
        sliderEmptyColor = DeepOcean_EmptySlider,
        sliderFillColor = DeepOcean_FillSlider,
        themeColorList = listOf(DeepOcean_GradientStart, DeepOcean_GradientEnd)
    )

    fun getThemeInfoList() : List<ThemeInfo> {
        return listOf(
            ThemeInfo(themeName = R.string.midnight, themeImage = R.drawable.midnight, Midnight),
            ThemeInfo(themeName = R.string.coralreef, themeImage = R.drawable.coralreef, CoralReef),
            ThemeInfo(themeName = R.string.forest, themeImage = R.drawable.forest, Forest),
            ThemeInfo(themeName = R.string.galaxynight, themeImage = R.drawable.galaxynight, GalaxyNight),
            ThemeInfo(themeName = R.string.monochrome, themeImage = R.drawable.monochrome, Monochrome),
            ThemeInfo(themeName = R.string.deepOncean, themeImage = R.drawable.deepocean, DeepOcean),
            ThemeInfo(themeName = R.string.pastel, themeImage = R.drawable.pastel, Pastel),
            ThemeInfo(themeName = R.string.oceancalm, themeImage = R.drawable.oceancalm, OceanCalm)
        )
    }
}