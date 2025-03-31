package com.dkproject.space_out.data.local

import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import com.dkproject.space_out.ThemeSetting
import com.dkproject.space_out.presentation.model.ThemeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ThemeRepository {
    fun getThemeSetting(): Flow<ThemeSetting>
    suspend fun updateThemeSetting(themeData: ThemeData): Result<Unit>
}

class ThemeRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<ThemeSetting>
): ThemeRepository {
    override fun getThemeSetting(): Flow<ThemeSetting> {
        return dataStore.data
    }

    override suspend fun updateThemeSetting(themeData: ThemeData): Result<Unit> = kotlin.runCatching {
        dataStore.updateData { old ->
            old.toBuilder()
                .setPrimaryColor(themeData.primaryColor.toArgb())
                .setSliderEmptyColor(themeData.sliderEmptyColor.toArgb())
                .setSliderFillColor(themeData.sliderFillColor.toArgb())
                .clearThemeColorList()
                .addAllThemeColorList(themeData.themeColorList.map { it.toArgb() })
                .build()
        }
    }
}