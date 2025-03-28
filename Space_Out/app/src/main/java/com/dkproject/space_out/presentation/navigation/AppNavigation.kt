package com.dkproject.space_out.presentation.navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dkproject.space_out.presentation.ui.sound.AudioViewModel
import com.dkproject.space_out.presentation.ui.sound.SoundCompactScreen
import com.dkproject.space_out.presentation.ui.sound.SoundExpandScreen
import com.dkproject.space_out.presentation.ui.video.VideoScreen
import com.dkproject.space_out.presentation.ui.video.VideoViewModel
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(
    navController: NavHostController,
    audioViewModel: AudioViewModel,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    val uiState by audioViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = AppDestinations.Screen.Sound) {
        composable<AppDestinations.Screen.Sound> {
            when(windowWidthSizeClass) {
                WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                    SoundCompactScreen(
                        modifier = Modifier.fillMaxSize(),
                        uiState = uiState,
                        updateVolume = audioViewModel::playOrUpdateSound,
                        startVideo = { path ->
                            navController.navigate(AppDestinations.Screen.Video(path))
                        }
                    )
                }
                WindowWidthSizeClass.Expanded -> {
                    SoundExpandScreen(uiState = uiState, updateVolume = audioViewModel::playOrUpdateSound) { path ->
                        navController.navigate(AppDestinations.Screen.Video(path))
                    }
                }
            }
        }

        composable<AppDestinations.Screen.Video> { backStackEntry ->
            val video: AppDestinations.Screen.Video = backStackEntry.toRoute()
            val path = "android.resource://${context.packageName}/${video.path}"
            val viewModel: VideoViewModel = hiltViewModel()
            VideoScreen(videoUri = path, viewModel = viewModel, windowWidthSizeClass = windowWidthSizeClass,
                soundUiState = uiState, updateVolume = audioViewModel::playOrUpdateSound) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
                navController.popBackStack()
            }
        }
    }
}

object AppDestinations {
    sealed class Screen() {
        @Serializable
        data object Sound : Screen()

        @Serializable
        data class Video(val path: String) : Screen()
    }
}