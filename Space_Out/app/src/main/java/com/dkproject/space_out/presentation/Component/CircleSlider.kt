package com.dkproject.space_out.presentation.Component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.theme.Space_OutTheme
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin


@Composable
fun CircleSlider(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float,
    @DrawableRes offImage: Int,
    @DrawableRes onImage: Int,
    onPositionChange: (Int) -> Unit
) {
    var tes = animateFloatAsState(targetValue = initialValue / 100f)

    // 원 중심 좌표
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    // 현재 슬라이더 값
    var positionValue by remember {
        mutableIntStateOf(initialValue)
    }

    // 드래그 중 터치 각도 변화량 기록
    var changeAngle by remember {
        mutableFloatStateOf(0f)
    }

    // 드래그가 시작될 때의 각도 저장 / 드래그 진행 중 현재 터치 각도와 비교할 때 기준으로 사용
    var dragStartedAngle by remember {
        mutableFloatStateOf(0f)
    }

    // 드래그가 시작하기 전의 슬라이더 값을 저장
    var oldPositionValue by remember {
        mutableIntStateOf(initialValue)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true){
                    detectDragGestures(
                        onDragStart = {offset ->
                            // 터치 시작점과 원 중심 간의 각도구하기 / 음수값 보정하고 0 ~ 360 범위로 정규화
                            dragStartedAngle = -atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat()
                            dragStartedAngle = (dragStartedAngle + 180f).mod(360f)
                        },
                        onDrag = { change, _ ->
                            // 드래그 중 현재 터치 위치의 각도를 계산하고 정규화
                            var touchAngle = -atan2(
                                x = circleCenter.y - change.position.y,
                                y = circleCenter.x - change.position.x
                            ) * (180f / PI).toFloat()
                            touchAngle = (touchAngle + 180f).mod(360f)

                            // 현재 각도와의 차이 계산
                            val currentAngle = positionValue * 360f / (maxValue - minValue)
                            val diff = (touchAngle - currentAngle + 540f) % 360f - 180f

                            // 슬라이더 증분 계산 및 업데이트
                            val delta = (diff / (360f / (maxValue - minValue))).roundToInt()
                            positionValue = (positionValue + delta).coerceIn(minValue, maxValue)

                            // 드래그가 지속되는 동안 터치 각도를 계속 업데이트하여, 다음 계싼 시 기준으로 활용
                            dragStartedAngle = touchAngle
                            onPositionChange(positionValue)
                        },
                        onDragEnd = {
                            oldPositionValue = positionValue
                            onPositionChange(positionValue)
                        }
                    )
                }
        ){
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter = Offset(x = width/2f, y = height/2f)

            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f/maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f)/2f,
                    (height - circleRadius * 2f)/2f
                )
            )

            val handleAngle = 90f + (360f / (maxValue - minValue)) * positionValue.toFloat()
            val handleAngleRad = Math.toRadians(handleAngle.toDouble())
            // 손잡이 크기 (필요에 따라 조절)
            val handleRadius = circleThickness * 1.2f
            val handleCenter = Offset(
                x = circleCenter.x + circleRadius * cos(handleAngleRad).toFloat(),
                y = circleCenter.y + circleRadius * sin(handleAngleRad).toFloat()
            )
            drawCircle(
                color = primaryColor,
                center = handleCenter,
                radius = handleRadius
            )
        }
        SoundFlameIcon(
            volume = initialValue,
            modifier = Modifier.size((circleRadius * 0.5).dp),
            onIcon = painterResource(onImage),
            offIcon = painterResource(offImage)
        )
//        AnimatedVisibility(initialValue > 0,
//            enter = fadeIn(),
//            exit = fadeOut()
//        ) {
//            Image(
//                painter = painterResource(onImage),
//                null,
//                modifier = Modifier.size((circleRadius * 0.5).dp),
//                contentScale = ContentScale.Crop,
//                alpha = if(tes.value < 0.2f) 0.2f else tes.value
//                )
//        }
//        AnimatedVisibility(initialValue == 0,
//            enter = fadeIn(),
//            exit = fadeOut()
//        ) {
//            Image(
//                painter = painterResource(offImage),
//                null,
//                modifier = Modifier.size((circleRadius * 0.5).dp),
//                contentScale = ContentScale.Crop,
//            )
//        }
    }
}


@Composable
@Preview(showBackground = true)
private fun CircleSliderPreview() {
    Space_OutTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CircleSlider(
                modifier = Modifier.size(150.dp),
                initialValue = 67,
                offImage = R.drawable.fireoff,
                onImage = R.drawable.fireon,
                primaryColor = Color.Green,
                secondaryColor = Color.LightGray,
                circleRadius = 150f,
            ) {

            }
        }
    }
}
