package com.dkproject.space_out.presentation.ui.sound

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.Component.SelectVideoBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundExpandScreen(
    uiState: SoundUiState,
    updateVolume: (String, Int) -> Unit,
    startVideo: (String) -> Unit
) {
    val context = LocalContext.current
    var isVideoSheet by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp / 3f
    val gridItemHeight = screenHeightDp.value.dp / 1.2f


    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0XFF1E3A8A), Color(0XFF155E75))
                )
            )
            .systemBarsPadding()
    ) {
        NavigationRail(containerColor = Color.Transparent) {
            NavigationRailItem(
                selected = false,
                onClick = { isVideoSheet = true },
                icon = {
                    Icon(
                        painterResource(R.drawable.baseline_slow_motion_video_24),
                        null,
                        tint = Color.White
                    )
                }
            )
        }
        LazyHorizontalGrid(rows = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(uiState.soundList) { sound ->
                SoundItem(
                    modifier = Modifier.width(screenHeightDp).height(screenHeightDp),
                    sound = sound,
                    circleRadius = gridItemHeight.value.coerceAtMost(180f),
                    updateVolume = updateVolume
                )
            }
        }
    }

    if (isVideoSheet) {
        AlertDialog(
            onDismissRequest = { isVideoSheet = false },
            confirmButton = {},
            text = {
                SelectVideoBottomSheet(startVideo = { path ->
                    isVideoSheet = false
                    startVideo(path)
                })
            },
            containerColor = Color(0xFF111722)
        )
    }
}