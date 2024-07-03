package com.codingle.newsoncompose.core_ui.component.shimmer

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.LocalShimmerTheme
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.Shimmer
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerArea
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerBounds
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerBounds.Custom
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerBounds.View
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerBounds.Window
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerEffect
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerModifier
import com.codingle.newsoncompose.core_ui.component.shimmer.ShimmerAttr.ShimmerTheme

@Composable
internal fun rememberMavShimmerBounds(
    shimmerBounds: ShimmerBounds,
): Rect? {
    val displayMetrics = LocalContext.current.resources.displayMetrics
    return remember(shimmerBounds, displayMetrics) {
        when (shimmerBounds) {
            Window -> Rect(
                0F,
                0F,
                displayMetrics.widthPixels.toFloat(),
                displayMetrics.heightPixels.toFloat()
            )

            Custom -> Rect.Zero
            View -> null
        }
    }
}

@Composable
internal fun rememberMavShimmerEffect(theme: ShimmerTheme): ShimmerEffect {
    val shimmerWidth = with(LocalDensity.current) { theme.shimmerWidth.toPx() }
    val shimmerEffect = remember(theme) {
        ShimmerEffect(
            animationSpec = theme.animationSpec,
            blendMode = theme.blendMode,
            rotation = theme.rotation,
            shaderColors = theme.shaderColors,
            shaderColorStops = theme.shaderColorStops,
            shimmerWidth = shimmerWidth,
        )
    }

    LaunchedEffect(shimmerEffect) { shimmerEffect.startAnimation() }
    return shimmerEffect
}

@Composable
fun rememberShimmer(
    shimmerBounds: ShimmerBounds,
    theme: ShimmerTheme = LocalShimmerTheme.current,
): Shimmer {
    val effect = rememberMavShimmerEffect(theme)
    val bounds = rememberMavShimmerBounds(shimmerBounds)

    return remember(theme, effect, bounds) { Shimmer(theme, effect, bounds) }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmer(
    customShimmer: Shimmer? = null
): Modifier = composed(
    factory = {
        val shimmer = customShimmer ?: rememberShimmer(View)

        val width = with(LocalDensity.current) { shimmer.theme.shimmerWidth.toPx() }
        val area = remember(width, shimmer.theme.rotation) { ShimmerArea(width, shimmer.theme.rotation) }
        LaunchedEffect(area, shimmer) { shimmer.boundsFlow.collect { area.updateBounds(it) } }
        remember(area, shimmer) { ShimmerModifier(area, shimmer.effect) }
    }
)