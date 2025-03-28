package com.dkproject.space_out.presentation.ui.sound

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.Component.CircleSlider
import com.dkproject.space_out.presentation.Component.SelectVideoBottomSheet
import com.dkproject.space_out.presentation.model.SoundInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundCompactScreen(
    modifier: Modifier = Modifier,
    uiState: SoundUiState,
    updateVolume: (String, Int) -> Unit,
    startVideo: (String) -> Unit
) {
    var isVideoSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true)
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenWidthDp.dp / 2f
    val gridItemHeight = screenHeightDp.coerceAtMost(280.dp)

    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0XFF1E3A8A), Color(0XFF155E75))
                )
            )
            .systemBarsPadding()
    ) {
        CenterAlignedTopAppBar(
            title = { Text("Space_Out") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
            actions = {
                IconButton(onClick = { isVideoSheet = true }) {
                    Icon(painterResource(R.drawable.baseline_slow_motion_video_24), null, tint = Color.White)
                }
            }
        )
        SoundCompactListView(uiState = uiState, updateVolume = updateVolume)
    }
    if (isVideoSheet) {
        ModalBottomSheet(onDismissRequest = { isVideoSheet = false},
            sheetState = sheetState,
            containerColor = Color(0XFF111722)) {
            SelectVideoBottomSheet(startVideo = { path ->
                isVideoSheet = false
                startVideo(path)
            })
        }
    }
}

@Composable
fun SoundCompactListView(
    uiState: SoundUiState,
    updateVolume: (String, Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenWidthDp.dp / 2f
    val gridItemHeight = screenHeightDp.coerceAtMost(280.dp)
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(32.dp)
    ) {
        items(uiState.soundList) { sound ->
            SoundItem(
                modifier = Modifier.height(gridItemHeight),
                sound = sound,
                circleRadius = gridItemHeight.value.coerceAtMost(180f),
                updateVolume = updateVolume
            )
        }
    }
}

@Composable
fun SoundItem(
    modifier: Modifier = Modifier,
    sound: SoundInfo,
    circleRadius: Float,
    updateVolume: (String, Int) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircleSlider(
            modifier = modifier,
            initialValue = sound.volume,
            primaryColor = Color.Green,
            secondaryColor = Color.Gray.copy(alpha = 0.5f),
            circleRadius = circleRadius
        ) { vol ->
            updateVolume(sound.fileName, vol)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(sound.soundName),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}