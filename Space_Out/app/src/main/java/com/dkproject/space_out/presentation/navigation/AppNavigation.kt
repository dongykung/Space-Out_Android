package com.dkproject.space_out.presentation.navigation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dkproject.space_out.ThemeData
import com.dkproject.space_out.ThemeSetting
import com.dkproject.space_out.data.local.ThemeSettingsSerializer
import com.dkproject.space_out.data.local.storeThemeSetting
import com.dkproject.space_out.presentation.ui.sound.AudioViewModel
import com.dkproject.space_out.presentation.ui.sound.SoundCompactScreen
import com.dkproject.space_out.presentation.ui.sound.SoundExpandScreen
import com.dkproject.space_out.presentation.ui.sound.SoundUiEvent
import com.dkproject.space_out.presentation.ui.splash.SplashView
import com.dkproject.space_out.presentation.ui.video.VideoScreen
import com.dkproject.space_out.presentation.ui.video.VideoViewModel
import com.dkproject.space_out.service.AudioService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    audioViewModel: AudioViewModel,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    var isStartedService by rememberSaveable {
        mutableStateOf(false)
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val activity = (context as? Activity)
    val uiState by audioViewModel.uiState.collectAsStateWithLifecycle()
    val themeColor by audioViewModel.themeData.collectAsStateWithLifecycle()

    LaunchedEffect(audioViewModel.uiEvent) {
        audioViewModel.uiEvent.collect { event ->
            when (event) {
                is SoundUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = AppDestinations.Screen.Splash) {
        composable<AppDestinations.Screen.Splash> {
            SplashView(modifier = Modifier.fillMaxSize(), navigateToHome = {
                navController.navigate(AppDestinations.Screen.Sound) {
                    popUpTo(AppDestinations.Screen.Splash) {
                        inclusive = true
                    }
                }
            })
        }
        composable<AppDestinations.Screen.Sound> {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val notificationPermissionState =
                    rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
                when (notificationPermissionState.status) {
                    is PermissionStatus.Denied -> {
                        notificationPermissionState.launchPermissionRequest()
                    }

                    PermissionStatus.Granted -> {
                        if (!isStartedService) {
                            context.startForegroundService(Intent(context, AudioService::class.java))
                            isStartedService = true
                        }
                    }
                }
            }
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                    SoundCompactScreen(
                        modifier = Modifier.fillMaxSize(),
                        uiState = uiState,
                        themeColor = themeColor,
                        updateVolume = audioViewModel::playOrUpdateSound,
                        startVideo = { path ->
                            navController.navigate(AppDestinations.Screen.Video(path))
                        },
                        updateTheme = audioViewModel::updateTheme
                    )
                }

                WindowWidthSizeClass.Expanded -> {
                    SoundExpandScreen(
                        uiState = uiState,
                        themeColor = themeColor,
                        updateVolume = audioViewModel::playOrUpdateSound,
                        startVideo = { path ->
                            navController.navigate(AppDestinations.Screen.Video(path))
                        },
                        updateTheme = audioViewModel::updateTheme
                    )
                }
            }
        }

        composable<AppDestinations.Screen.Video> { backStackEntry ->
            val video: AppDestinations.Screen.Video = backStackEntry.toRoute()
            val path = "android.resource://${context.packageName}/${video.path}"
            val viewModel: VideoViewModel = hiltViewModel()
            VideoScreen(
                videoUri = path,
                viewModel = viewModel,
                soundUiState = uiState,
                themeColor = themeColor,
                updateVolume = audioViewModel::playOrUpdateSound
            ) {
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
        data object Splash : Screen()

        @Serializable
        data class Video(val path: String) : Screen()
    }
}