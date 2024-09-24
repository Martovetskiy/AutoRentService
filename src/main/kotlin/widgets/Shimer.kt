package widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    shape: Shape,
    content: @Composable () -> Unit
) {
    val transition = rememberInfiniteTransition()
    val shimmerAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing)
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color.Gray.copy(alpha = 0.2f), Color.Gray.copy(alpha = 0.5f), Color.Gray.copy(alpha = 0.2f)),
        start = Offset.Zero,
        end = Offset(200f, 0f).copy(x = 1000f * shimmerAnimation.value)
    )

    Box(
        modifier = modifier
            .background(brush, shape = shape) // Используйте вашу собственную реализацию `placeholder`
    ) {
        content()
    }
}
