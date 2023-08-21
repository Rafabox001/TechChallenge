package com.example.baubaptechchallenge.presentation.login.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginErrorLabel(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error,
        textAlign = textAlign
    )
}

@Composable
@Preview
fun BaubapErrorLabelPreview() {
    LoginErrorLabel(modifier = Modifier.wrapContentWidth(), text = "My error")
}