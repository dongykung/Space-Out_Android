package com.dkproject.space_out.presentation.ui.video

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.Component.SelectVideoBottomSheet
import com.dkproject.space_out.presentation.ui.sound.SoundCompactListView
import com.dkproject.space_out.presentation.ui.sound.SoundUiState
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(
    videoUri: String,
    viewModel: VideoViewModel = hiltViewModel(),
    soundUiState: SoundUiState,
    windowWidthSizeClass: WindowWidthSizeClass,
    updateVolume: (String, Int) -> Unit,
    onBackClick: () -> Unit
) {
    var isAudioController by remember { mutableStateOf(false) }
    var isControlVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = (context as? Activity)
    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_START) }
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(videoUri) {
        viewModel.preparePlayer(videoUri)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    LaunchedEffect(isControlVisible) {
        if (isControlVisible) {
            delay(3000)
            isControlVisible = false
        }
    }

    // 생명주기 종료 시 Exoplayer 메모리 해제
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { isControlVisible = !isControlVisible }) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = viewModel.exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    useController = false
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        it.player?.play()
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    else -> Unit
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        AnimatedVisibility(
            visible = isControlVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // 어두워짐
            ) {
                Column(modifier = Modifier.systemBarsPadding()) {
                    Row {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { isAudioController = true }) {
                            Icon(painterResource(R.drawable.baseline_slow_motion_video_24), null, tint = Color.White)
                        }
                    }
                }
            }
        }
    }

    if (isAudioController) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                ModalBottomSheet(onDismissRequest = { isAudioController = false }) {
                    SoundCompactListView(uiState = soundUiState, updateVolume = updateVolume)
                }
            }
            WindowWidthSizeClass.Expanded -> {
                Dialog(
                    onDismissRequest = { isAudioController = false },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        SoundCompactListView(uiState = soundUiState, updateVolume = updateVolume)
                    }
                }
            }
        }
    }
}