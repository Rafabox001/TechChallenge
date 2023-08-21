package com.example.baubaptechchallenge.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    class StringResource(val resId: Int, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when(this) {
            is StringResource -> stringResource(id = resId, *args)
            is DynamicString -> value
        }
    }
}