package com.dkproject.space_out.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.dkproject.space_out.ThemeSetting

val Context.storeThemeSetting: DataStore<ThemeSetting> by dataStore(
    fileName = "theme_data.pb",
    serializer = ThemeSettingsSerializer
)