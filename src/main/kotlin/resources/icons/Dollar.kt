package resources.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val DollarSvgrepoCom: ImageVector
    get() {
        if (_DollarSvgrepoCom != null) {
            return _DollarSvgrepoCom!!
        }
        _DollarSvgrepoCom = ImageVector.Builder(
            name = "DollarSvgrepoCom",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.216f, 15f)
                lineTo(6.105f, 16f)
                horizontalLineTo(8.117f)
                lineTo(8.228f, 15f)
                horizontalLineTo(10.003f)
                curveTo(12.21f, 15f, 14f, 13.21f, 14f, 11.003f)
                curveTo(14f, 9.13f, 12.699f, 7.508f, 10.87f, 7.101f)
                lineTo(9.149f, 6.719f)
                lineTo(9.451f, 4f)
                horizontalLineTo(13f)
                verticalLineTo(1f)
                horizontalLineTo(9.784f)
                lineTo(9.895f, 0f)
                horizontalLineTo(7.883f)
                lineTo(7.772f, 1f)
                horizontalLineTo(5.997f)
                curveTo(3.79f, 1f, 2f, 2.79f, 2f, 4.997f)
                curveTo(2f, 6.87f, 3.301f, 8.492f, 5.13f, 8.899f)
                lineTo(6.851f, 9.281f)
                lineTo(6.549f, 12f)
                horizontalLineTo(3f)
                verticalLineTo(15f)
                horizontalLineTo(6.216f)
                close()
                moveTo(8.562f, 12f)
                horizontalLineTo(10.003f)
                curveTo(10.554f, 12f, 11f, 11.554f, 11f, 11.003f)
                curveTo(11f, 10.536f, 10.675f, 10.131f, 10.219f, 10.03f)
                lineTo(8.815f, 9.718f)
                lineTo(8.562f, 12f)
                close()
                moveTo(7.185f, 6.282f)
                lineTo(7.438f, 4f)
                horizontalLineTo(5.997f)
                curveTo(5.446f, 4f, 5f, 4.446f, 5f, 4.997f)
                curveTo(5f, 5.464f, 5.325f, 5.869f, 5.781f, 5.97f)
                lineTo(7.185f, 6.282f)
                close()
            }
        }.build()

        return _DollarSvgrepoCom!!
    }

@Suppress("ObjectPropertyName")
private var _DollarSvgrepoCom: ImageVector? = null
