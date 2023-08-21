package com.example.baubaptechchallenge.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val zero: Dp = 0.dp,
    val one: Dp = 1.dp,
    val xxxSmall: Dp = 2.dp,
    val xxSmall: Dp = 4.dp,
    val xSmall: Dp = 8.dp,
    val small: Dp = 12.dp,
    val normal: Dp = 16.dp,
    val medium: Dp = 20.dp,
    val large: Dp = 24.dp,
    val xLarge: Dp = 28.dp,
    val xxLarge: Dp = 32.dp,
    val xxxLarge: Dp = 36.dp,
    val wide: Dp = 40.dp,
    val xWide: Dp = 44.dp,
    val xxWide: Dp = 48.dp,
    val xxxWide: Dp = 52.dp,
    val vast: Dp = 56.dp,
    val xVast: Dp = 60.dp,
    val xxVast: Dp = 64.dp,
    val xxxVast: Dp = 68.dp,
    val extensive: Dp = 72.dp
)

val LocalDimens = compositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current