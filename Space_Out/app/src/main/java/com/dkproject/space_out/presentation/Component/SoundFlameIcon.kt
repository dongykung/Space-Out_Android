package com.dkproject.space_out.presentation.Component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.dkproject.space_out.R

@Composable
fun SoundFlameIcon(
    volume: Int, // 0 ~ 100
    modifier: Modifier = Modifier,
    onIcon: Painter,
    offIcon: Painter,
) {
    val fireRatio by animateFloatAsState(
        targetValue = volume / 100f,
        animationSpec = tween(durationMillis = 50)
    )
    Box(modifier = modifier) {

        Image(
            painter = offIcon,
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Image(
            painter = onIcon,
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(TopToBottomClipShape(fireRatio))
        )
    }
}

class TopToBottomClipShape(private val ratio: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val height = size.height * ratio
        val rect = Rect(0f, size.height - height, size.width, size.height)
        return Outline.Rectangle(rect)
    }
}

@Composable
@Preview(showBackground = true)
private fun SoundFlameIconPreview() {
    val value by remember { mutableIntStateOf(100) }
    SoundFlameIcon(
        volume = 20,
        onIcon = painterResource(R.drawable.fireon),
        offIcon = painterResource(R.drawable.fireoff)
    )
}