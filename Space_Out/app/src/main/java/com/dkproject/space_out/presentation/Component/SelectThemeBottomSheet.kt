package com.dkproject.space_out.presentation.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.dkproject.space_out.presentation.model.ThemeInfo
import com.dkproject.space_out.presentation.theme.Space_OutTheme
import com.dkproject.space_out.presentation.theme.bottomSheetBackground
import com.dkproject.space_out.presentation.util.ThemeUtil

@Composable
fun SelectThemeBottomSheet(
    modifier: Modifier = Modifier,
    themeClick: (ThemeInfo) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val gridItemSize = screenHeightDp / 6.5f
    Column(modifier = modifier.background(bottomSheetBackground)) {
        val themes: List<ThemeInfo> = ThemeUtil.getThemeInfoList()
        LazyVerticalGrid(
            modifier = Modifier.padding(bottom = 16.dp),
            columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(themes, key = { it.themeName }) { theme ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(modifier = Modifier
                        .clickable { themeClick(theme) }
                        .size(gridItemSize),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.cardElevation(2.dp),
                        ) {
                        Image(
                            painter = painterResource(theme.themeImage), null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                    Text(
                        text = stringResource(theme.themeName),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SelectThemeBottomSheetPreview() {
    Space_OutTheme {
        SelectThemeBottomSheet {  }
    }
}