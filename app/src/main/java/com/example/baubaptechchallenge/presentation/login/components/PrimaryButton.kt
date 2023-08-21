package com.example.baubaptechchallenge.presentation.login.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.baubaptechchallenge.presentation.theme.dimens

@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .height(MaterialTheme.dimens.xxWide),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.extensive),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
@Preview
fun PrimaryButtonPreview() {
    PrimaryButton(text = "Hello") {}
}