package icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val Close: ImageVector
    get() {
        if (_Close != null) {
            return _Close!!
        }
        _Close = ImageVector.Builder(
            name = "Close",
            defaultWidth = 512.dp,
            defaultHeight = 512.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveToRelative(15.707f, 9.707f)
                lineToRelative(-2.293f, 2.293f)
                lineToRelative(2.293f, 2.293f)
                curveToRelative(0.391f, 0.391f, 0.391f, 1.023f, 0f, 1.414f)
                curveToRelative(-0.195f, 0.195f, -0.451f, 0.293f, -0.707f, 0.293f)
                reflectiveCurveToRelative(-0.512f, -0.098f, -0.707f, -0.293f)
                lineToRelative(-2.293f, -2.293f)
                lineToRelative(-2.293f, 2.293f)
                curveToRelative(-0.195f, 0.195f, -0.451f, 0.293f, -0.707f, 0.293f)
                reflectiveCurveToRelative(-0.512f, -0.098f, -0.707f, -0.293f)
                curveToRelative(-0.391f, -0.391f, -0.391f, -1.023f, 0f, -1.414f)
                lineToRelative(2.293f, -2.293f)
                lineToRelative(-2.293f, -2.293f)
                curveToRelative(-0.391f, -0.391f, -0.391f, -1.023f, 0f, -1.414f)
                reflectiveCurveToRelative(1.023f, -0.391f, 1.414f, 0f)
                lineToRelative(2.293f, 2.293f)
                lineToRelative(2.293f, -2.293f)
                curveToRelative(0.391f, -0.391f, 1.023f, -0.391f, 1.414f, 0f)
                reflectiveCurveToRelative(0.391f, 1.023f, 0f, 1.414f)
                close()
                moveTo(24f, 12f)
                curveToRelative(0f, 6.617f, -5.383f, 12f, -12f, 12f)
                reflectiveCurveTo(0f, 18.617f, 0f, 12f)
                reflectiveCurveTo(5.383f, 0f, 12f, 0f)
                reflectiveCurveToRelative(12f, 5.383f, 12f, 12f)
                close()
                moveTo(22f, 12f)
                curveToRelative(0f, -5.514f, -4.486f, -10f, -10f, -10f)
                reflectiveCurveTo(2f, 6.486f, 2f, 12f)
                reflectiveCurveToRelative(4.486f, 10f, 10f, 10f)
                reflectiveCurveToRelative(10f, -4.486f, 10f, -10f)
                close()
            }
        }.build()

        return _Close!!
    }

@Suppress("ObjectPropertyName")
private var _Close: ImageVector? = null
