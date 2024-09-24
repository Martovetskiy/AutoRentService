package resources.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val CarSvgrepoCom: ImageVector
    get() {
        if (_CarSvgrepoCom != null) {
            return _CarSvgrepoCom!!
        }
        _CarSvgrepoCom = ImageVector.Builder(
            name = "CarSvgrepoCom",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(16f, 6f)
                lineToRelative(3f, 4f)
                horizontalLineToRelative(2f)
                curveToRelative(1.11f, 0f, 2f, 0.89f, 2f, 2f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-2f)
                curveToRelative(0f, 1.66f, -1.34f, 3f, -3f, 3f)
                reflectiveCurveToRelative(-3f, -1.34f, -3f, -3f)
                horizontalLineTo(9f)
                curveToRelative(0f, 1.66f, -1.34f, 3f, -3f, 3f)
                reflectiveCurveToRelative(-3f, -1.34f, -3f, -3f)
                horizontalLineTo(1f)
                verticalLineToRelative(-3f)
                curveToRelative(0f, -1.11f, 0.89f, -2f, 2f, -2f)
                lineToRelative(3f, -4f)
                horizontalLineTo(16f)
                moveTo(10.5f, 7.5f)
                horizontalLineTo(6.75f)
                lineTo(4.86f, 10f)
                horizontalLineToRelative(5.64f)
                verticalLineTo(7.5f)
                moveTo(12f, 7.5f)
                verticalLineTo(10f)
                horizontalLineToRelative(5.14f)
                lineToRelative(-1.89f, -2.5f)
                horizontalLineTo(12f)
                moveTo(6f, 13.5f)
                curveToRelative(-0.83f, 0f, -1.5f, 0.67f, -1.5f, 1.5f)
                reflectiveCurveToRelative(0.67f, 1.5f, 1.5f, 1.5f)
                reflectiveCurveToRelative(1.5f, -0.67f, 1.5f, -1.5f)
                reflectiveCurveTo(6.83f, 13.5f, 6f, 13.5f)
                moveTo(18f, 13.5f)
                curveToRelative(-0.83f, 0f, -1.5f, 0.67f, -1.5f, 1.5f)
                reflectiveCurveToRelative(0.67f, 1.5f, 1.5f, 1.5f)
                reflectiveCurveToRelative(1.5f, -0.67f, 1.5f, -1.5f)
                reflectiveCurveTo(18.83f, 13.5f, 18f, 13.5f)
                close()
            }
        }.build()

        return _CarSvgrepoCom!!
    }

@Suppress("ObjectPropertyName")
private var _CarSvgrepoCom: ImageVector? = null
