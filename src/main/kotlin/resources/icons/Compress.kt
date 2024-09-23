package icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val Compress: ImageVector
    get() {
        if (_Compress != null) {
            return _Compress!!
        }
        _Compress = ImageVector.Builder(
            name = "Compress",
            defaultWidth = 512.dp,
            defaultHeight = 512.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(7.293f, 2.091f)
                lineTo(5.4f, 3.985f)
                lineTo(1.707f, 0.293f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.293f, 1.707f)
                lineTo(3.985f, 5.4f)
                lineTo(2.091f, 7.293f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.8f, 9f)
                horizontalLineTo(7.564f)
                arcTo(1.436f, 1.436f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9f, 7.564f)
                verticalLineTo(2.8f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 7.293f, 2.091f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(20.015f, 18.6f)
                lineToRelative(1.894f, -1.894f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 21.2f, 15f)
                horizontalLineTo(16.436f)
                arcTo(1.436f, 1.436f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15f, 16.436f)
                verticalLineTo(21.2f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.707f, 0.708f)
                lineTo(18.6f, 20.015f)
                lineToRelative(3.692f, 3.692f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.414f, -1.414f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(16.436f, 9f)
                horizontalLineTo(21.2f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.708f, -1.707f)
                lineTo(20.015f, 5.4f)
                lineToRelative(3.692f, -3.692f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 22.293f, 0.293f)
                lineTo(18.6f, 3.985f)
                lineTo(16.707f, 2.091f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15f, 2.8f)
                verticalLineTo(7.564f)
                arcTo(1.436f, 1.436f, 0f, isMoreThanHalf = false, isPositiveArc = false, 16.436f, 9f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(7.564f, 15f)
                horizontalLineTo(2.8f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.708f, 1.707f)
                lineTo(3.985f, 18.6f)
                lineTo(0.293f, 22.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = false, 1.414f, 1.414f)
                lineTo(5.4f, 20.015f)
                lineToRelative(1.894f, 1.894f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9f, 21.2f)
                verticalLineTo(16.436f)
                arcTo(1.436f, 1.436f, 0f, isMoreThanHalf = false, isPositiveArc = false, 7.564f, 15f)
                close()
            }
        }.build()

        return _Compress!!
    }

@Suppress("ObjectPropertyName")
private var _Compress: ImageVector? = null
