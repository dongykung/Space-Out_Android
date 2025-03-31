package com.dkproject.space_out.data.local

import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.Serializer
import com.dkproject.space_out.ThemeSetting
import com.dkproject.space_out.presentation.util.ThemeUtil
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object ThemeSettingsSerializer : Serializer<ThemeSetting> {
    private val basicTheme = ThemeUtil.Monochrome
    override val defaultValue: ThemeSetting
        get() = ThemeSetting.newBuilder()
            .setPrimaryColor(basicTheme.primaryColor.toArgb())
            .setSliderEmptyColor(basicTheme.sliderEmptyColor.toArgb())
            .setSliderFillColor(basicTheme.sliderFillColor.toArgb())
            .addAllThemeColorList(basicTheme.themeColorList.map { it.toArgb() })
            .build()

    override suspend fun readFrom(input: InputStream): ThemeSetting {
            return try {
                ThemeSetting.parseFrom(input)
            } catch (e: InvalidProtocolBufferException) {
                defaultValue
            }
    }

    override suspend fun writeTo(t: ThemeSetting, output: OutputStream) {
        t.writeTo(output)
    }

}

