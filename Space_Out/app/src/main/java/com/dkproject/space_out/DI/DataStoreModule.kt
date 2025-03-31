package com.dkproject.space_out.DI

import android.content.Context
import androidx.datastore.core.DataStore
import com.dkproject.space_out.ThemeSetting
import com.dkproject.space_out.data.local.ThemeRepository
import com.dkproject.space_out.data.local.ThemeRepositoryImpl
import com.dkproject.space_out.data.local.storeThemeSetting
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideThemeSettingDataStore(
        @ApplicationContext context: Context
    ): DataStore<ThemeSetting> {
        return context.storeThemeSetting
    }

    @Provides
    @Singleton
    fun provideThemeRepository(
        dataStore: DataStore<ThemeSetting>
    ): ThemeRepository {
        return ThemeRepositoryImpl(dataStore)
    }
}