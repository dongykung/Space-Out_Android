package com.dkproject.space_out.presentation.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dkproject.space_out.presentation.model.VideoInfo
import com.dkproject.space_out.presentation.theme.Space_OutTheme
import com.dkproject.space_out.presentation.util.VideoUtil

@Composable
fun ExpandedVideoDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onVideoClick: (String) -> Unit = {}
) {
    val videos: List<VideoInfo> = VideoUtil.getVideoDataList()
    val configuration = LocalConfiguration.current
    val gridItemSize = configuration.screenHeightDp.dp / 4
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 48.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.Black.copy(alpha = 0.3f)
        ) {
            LazyHorizontalGrid(modifier = Modifier.padding(12.dp),rows = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                items(videos, key = { it.id }) { video ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(onClick = { onVideoClick(video.videoPath) }, modifier = Modifier.size(gridItemSize)) {
                            Image(
                                painterResource(video.thumbnail), null,
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            stringResource(video.videoName),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ExpandedVideoDialogPreview() {
    Space_OutTheme {
        ExpandedVideoDialog()
    }
}