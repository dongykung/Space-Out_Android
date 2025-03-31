package com.dkproject.space_out.presentation.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dkproject.space_out.presentation.model.ThemeData
import com.dkproject.space_out.presentation.model.ThemeInfo
import com.dkproject.space_out.presentation.util.ThemeUtil

@Composable
fun ExpandedThemeDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onThemeClick: (ThemeInfo) -> Unit = {},
) {
    val themes = ThemeUtil.getThemeInfoList()
    val configuration = LocalConfiguration.current
    val gridItemSize = configuration.screenHeightDp.dp / 4
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 48.dp),
            color = Color.Black.copy(alpha = 0.3f),
            shape = RoundedCornerShape(24.dp),
        ) {
            LazyHorizontalGrid(
                modifier = Modifier.padding(12.dp),
                rows = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(themes, key = { it.themeName }) { theme ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            onClick = { onThemeClick(theme) },
                            modifier = Modifier.size(gridItemSize),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Image(
                                painterResource(theme.themeImage), null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                )
                        }
                        Text(
                            stringResource(theme.themeName),
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