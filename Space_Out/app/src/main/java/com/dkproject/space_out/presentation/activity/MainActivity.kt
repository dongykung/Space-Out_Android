package com.dkproject.space_out.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dkproject.space_out.presentation.navigation.AppNavigation
import com.dkproject.space_out.presentation.theme.Space_OutTheme
import com.dkproject.space_out.presentation.ui.sound.AudioViewModel
import com.dkproject.space_out.service.AudioService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Space_OutTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val windowSize = calculateWindowSizeClass(this)
                    val viewModel: AudioViewModel = hiltViewModel()
                    AppNavigation(
                        navController = rememberNavController(),
                        audioViewModel = viewModel,
                        windowWidthSizeClass = windowSize.widthSizeClass
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, AudioService::class.java))
    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Space_OutTheme {

    }
}