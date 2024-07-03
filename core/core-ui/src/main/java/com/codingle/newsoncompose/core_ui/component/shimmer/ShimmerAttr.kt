package com.codingle.newsoncompose.core_ui.component.shimmer

import android.graphics.Matrix
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.OnGloballyPositionedModifier
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

object ShimmerAttr {
    private const val DEFAULT_START_ALPHA = 0.25F
    private const val DEFAULT_MID_ALPHA = 1F
    private const val DEFAULT_END_ALPHA = 0.25F
    private const val DEFAULT_SHIMMER_DURATION = 800
    private const val DEFAULT_SHIMMER_DELAY = 600
    private const val DEFAULT_ROTATION = 0F
    private const val DEFAULT_START_STOPS = 0F
    private const val DEFAULT_MID_STOPS = 1.5F
    private const val DEFAULT_END_STOPS = 1F
    private const val HASH_CODE = 31
    private const val ROTATION_180 = 180
    private const val ROTATION_90 = 90
    private const val PHI_NUMBER = 3.1415927F

    sealed class ShimmerBounds {
        data object Custom : ShimmerBounds()
        data object View : ShimmerBounds()
        data object Window : ShimmerBounds()
    }

    data class ShimmerTheme(
        val animationSpec: AnimationSpec<Float>,
        val blendMode: BlendMode,
        val rotation: Float,
        val shaderColors: List<Color>,
        val shaderColorStops: List<Float>?,
        val shimmerWidth: Dp
    )

    internal class ShimmerArea(
        private val widthOfShimmer: Float,
        rotationInDegree: Float,
    ) {

        private val reducedRotation = rotationInDegree.reduceRotation().toRadian()

        private var requestedShimmerBounds: Rect? = null
        private var shimmerSize: Size = Size.Zero

        var translationDistance = 0F
            private set

        var pivotPoint = Offset.Unspecified
            private set

        var shimmerBounds = Rect.Zero
            private set

        var viewBounds = Rect.Zero
            set(value) {
                if (value == field) return
                field = value
                computeShimmerBounds()
            }

        fun updateBounds(shimmerBounds: Rect?) {
            if (this.requestedShimmerBounds == shimmerBounds) return
            requestedShimmerBounds = shimmerBounds
            computeShimmerBounds()
        }

        private fun computeShimmerBounds() {
            if (viewBounds.isEmpty) return
            shimmerBounds = requestedShimmerBounds ?: viewBounds
            pivotPoint = -viewBounds.topLeft + shimmerBounds.center
            val newShimmerSize = shimmerBounds.size
            if (shimmerSize != newShimmerSize) {
                shimmerSize = newShimmerSize
                computeTranslationDistance()
            }
        }

        private fun computeTranslationDistance() {
            val width = shimmerSize.width / 2
            val height = shimmerSize.height / 2

            val distanceCornerToCenter = sqrt(width.pow(2) + height.pow(2))
            val beta = acos(width / distanceCornerToCenter)
            val alpha = beta - reducedRotation

            val distanceCornerToRotatedCenterLine = cos(alpha) * distanceCornerToCenter
            translationDistance = distanceCornerToRotatedCenterLine * 2 + widthOfShimmer
        }

        private fun Float.reduceRotation(): Float {
            require(this >= 0F) { "The shimmer's rotation must be a positive number" }
            var rotation = this % ROTATION_180
            rotation -= ROTATION_90
            rotation = -abs(rotation)
            return rotation + ROTATION_90
        }

        private fun Float.toRadian(): Float = this / ROTATION_180 * PHI_NUMBER

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ShimmerArea

            if (widthOfShimmer != other.widthOfShimmer) return false
            if (reducedRotation != other.reducedRotation) return false

            return true
        }

        override fun hashCode(): Int {
            var result = widthOfShimmer.hashCode()
            result = HASH_CODE * result + reducedRotation.hashCode()
            return result
        }
    }

    internal class ShimmerEffect(
        private val animationSpec: AnimationSpec<Float>,
        private val blendMode: BlendMode,
        private val rotation: Float,
        private val shaderColors: List<Color>,
        private val shaderColorStops: List<Float>?,
        private val shimmerWidth: Float
    ) {

        private val animatedState = Animatable(0F)

        private val transformationMatrix = Matrix()

        private val shader = LinearGradientShader(
            from = Offset(-shimmerWidth / 2, 0F),
            to = Offset(shimmerWidth / 2, 0F),
            colors = shaderColors,
            colorStops = shaderColorStops,
        )

        private val paint = Paint().apply {
            isAntiAlias = true
            style = PaintingStyle.Fill
            blendMode = this@ShimmerEffect.blendMode
            shader = this@ShimmerEffect.shader
        }

        internal suspend fun startAnimation() {
            animatedState.animateTo(
                targetValue = 1F,
                animationSpec = animationSpec,
            )
        }

        private val emptyPaint = Paint()

        fun ContentDrawScope.draw(shimmerArea: ShimmerArea) = with(shimmerArea) {
            if (shimmerBounds.isEmpty || viewBounds.isEmpty) return

            val progress = animatedState.value
            val traversal =
                -translationDistance / 2 + translationDistance * progress + pivotPoint.x

            transformationMatrix.apply {
                reset()
                postTranslate(traversal, 0F)
                postRotate(rotation, pivotPoint.x, pivotPoint.y)
            }
            shader.setLocalMatrix(transformationMatrix)

            val drawArea = size.toRect()
            drawIntoCanvas { canvas ->
                canvas.withSaveLayer(
                    bounds = drawArea,
                    emptyPaint
                ) {
                    drawContent()
                    canvas.drawRect(drawArea, paint)
                }
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ShimmerEffect

            if (animationSpec != other.animationSpec) return false
            if (blendMode != other.blendMode) return false
            if (rotation != other.rotation) return false
            if (shaderColors != other.shaderColors) return false
            if (shaderColorStops != other.shaderColorStops) return false
            if (shimmerWidth != other.shimmerWidth) return false

            return true
        }

        override fun hashCode(): Int {
            var result = animationSpec.hashCode()
            result = HASH_CODE * result + blendMode.hashCode()
            result = HASH_CODE * result + rotation.hashCode()
            result = HASH_CODE * result + shaderColors.hashCode()
            result = HASH_CODE * result + shaderColorStops.hashCode()
            result = HASH_CODE * result + shimmerWidth.hashCode()
            return result
        }
    }

    class Shimmer internal constructor(
        internal val theme: ShimmerTheme,
        internal val effect: ShimmerEffect,
        bounds: Rect?
    ) {

        internal val boundsFlow = MutableStateFlow(bounds)

        fun updateBounds(bounds: Rect?) {
            boundsFlow.value = bounds
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Shimmer

            if (theme != other.theme) return false
            if (effect != other.effect) return false

            return true
        }

        override fun hashCode(): Int {
            var result = theme.hashCode()
            result = HASH_CODE * result + effect.hashCode()
            return result
        }
    }

    internal class ShimmerModifier(
        private val area: ShimmerArea,
        private val effect: ShimmerEffect,
    ) : DrawModifier, OnGloballyPositionedModifier {

        override fun ContentDrawScope.draw() {
            with(effect) { draw(area) }
        }

        override fun onGloballyPositioned(coordinates: LayoutCoordinates) {
            val viewBounds = coordinates.unclippedBoundsInWindow()
            area.viewBounds = viewBounds
        }
    }

    private val defaultShimmerTheme: ShimmerTheme = ShimmerTheme(
        animationSpec = infiniteRepeatable(
            animation = tween(
                DEFAULT_SHIMMER_DURATION,
                easing = LinearEasing,
                delayMillis = DEFAULT_SHIMMER_DELAY
            ),
            repeatMode = RepeatMode.Restart,
        ),
        blendMode = BlendMode.DstIn,
        rotation = DEFAULT_ROTATION,
        shaderColors = listOf(
            Color.Unspecified.copy(alpha = DEFAULT_START_ALPHA),
            Color.Unspecified.copy(alpha = DEFAULT_MID_ALPHA),
            Color.Unspecified.copy(alpha = DEFAULT_END_ALPHA)
        ),
        shaderColorStops = listOf(
            DEFAULT_START_STOPS,
            DEFAULT_MID_STOPS,
            DEFAULT_END_STOPS
        ),
        shimmerWidth = 400.dp
    )

    private fun LayoutCoordinates.unclippedBoundsInWindow(): Rect {
        val positionInWindow = positionInWindow()
        return Rect(
            positionInWindow.x,
            positionInWindow.y,
            positionInWindow.x + size.width,
            positionInWindow.y + size.height,
        )
    }

    val LocalShimmerTheme = staticCompositionLocalOf { defaultShimmerTheme }
}