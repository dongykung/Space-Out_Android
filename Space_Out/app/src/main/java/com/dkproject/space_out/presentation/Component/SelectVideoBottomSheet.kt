package com.dkproject.space_out.presentation.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkproject.space_out.presentation.theme.Space_OutTheme
import com.dkproject.space_out.presentation.util.VideoUtil

@Composable
fun SelectVideoBottomSheet(
    modifier: Modifier = Modifier,
    startVideo: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val gridItemSize = screenHeightDp / 6.5f
    Column(modifier = modifier.background(Color(0XFF111722))) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement =
            Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(VideoUtil.getVideoDataList(), key = { it.id }) { video ->
                Column(modifier = Modifier.width(gridItemSize), horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(modifier = Modifier.size(gridItemSize),
                        onClick = {startVideo( video.videoPath )}) {
                        Image(
                            painter = painterResource(video.thumbnail),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        stringResource(video.videoName),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SelectVideoBottomSheetPreview() {
    Space_OutTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            SelectVideoBottomSheet(modifier = Modifier.fillMaxSize()) {}
        }
    }
}