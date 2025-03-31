package com.dkproject.space_out.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.theme.Space_OutTheme
import com.dkproject.space_out.presentation.theme.subTitleColor
import kotlinx.coroutines.delay

@Composable
fun SplashView(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
) {

    LaunchedEffect(Unit) {
        delay(1200)
        navigateToHome()
    }
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.splash),
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(screenHeightDp / 4f))
            Image(
                painter = painterResource(R.drawable.appicon),
                null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = subTitleColor
            )
            CircularProgressIndicator(modifier = Modifier)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SplashViewPreview() {
    Space_OutTheme {
        SplashView() {}
    }
}