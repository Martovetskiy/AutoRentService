package resources.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val FilterSvgrepoCom: ImageVector
    get() {
        if (_FilterSvgrepoCom != null) {
            return _FilterSvgrepoCom!!
        }
        _FilterSvgrepoCom = ImageVector.Builder(
            name = "FilterSvgrepoCom",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(3f, 4.6f)
                curveTo(3f, 4.04f, 3f, 3.76f, 3.109f, 3.546f)
                curveTo(3.205f, 3.358f, 3.358f, 3.205f, 3.546f, 3.109f)
                curveTo(3.76f, 3f, 4.04f, 3f, 4.6f, 3f)
                horizontalLineTo(19.4f)
                curveTo(19.96f, 3f, 20.24f, 3f, 20.454f, 3.109f)
                curveTo(20.642f, 3.205f, 20.795f, 3.358f, 20.891f, 3.546f)
                curveTo(21f, 3.76f, 21f, 4.04f, 21f, 4.6f)
                verticalLineTo(6.337f)
                curveTo(21f, 6.582f, 21f, 6.704f, 20.972f, 6.819f)
                curveTo(20.948f, 6.921f, 20.907f, 7.019f, 20.853f, 7.108f)
                curveTo(20.791f, 7.209f, 20.704f, 7.296f, 20.531f, 7.469f)
                lineTo(14.469f, 13.531f)
                curveTo(14.296f, 13.704f, 14.209f, 13.791f, 14.147f, 13.892f)
                curveTo(14.092f, 13.981f, 14.052f, 14.079f, 14.028f, 14.181f)
                curveTo(14f, 14.296f, 14f, 14.418f, 14f, 14.663f)
                verticalLineTo(17f)
                lineTo(10f, 21f)
                verticalLineTo(14.663f)
                curveTo(10f, 14.418f, 10f, 14.296f, 9.972f, 14.181f)
                curveTo(9.948f, 14.079f, 9.907f, 13.981f, 9.853f, 13.892f)
                curveTo(9.791f, 13.791f, 9.704f, 13.704f, 9.531f, 13.531f)
                lineTo(3.469f, 7.469f)
                curveTo(3.296f, 7.296f, 3.209f, 7.209f, 3.147f, 7.108f)
                curveTo(3.093f, 7.019f, 3.052f, 6.921f, 3.028f, 6.819f)
                curveTo(3f, 6.704f, 3f, 6.582f, 3f, 6.337f)
                verticalLineTo(4.6f)
                close()
            }
        }.build()

        return _FilterSvgrepoCom!!
    }

@Suppress("ObjectPropertyName")
private var _FilterSvgrepoCom: ImageVector? = null
