package com.example.baubaptechchallenge.presentation.login.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Tsunami
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.baubaptechchallenge.R.string
import com.example.baubaptechchallenge.presentation.login.DIALOG_TEST_TAG
import com.example.baubaptechchallenge.presentation.theme.dimens
import com.example.baubaptechchallenge.presentation.theme.md_theme_light_onPrimaryContainer

@Composable
fun CustomDialog(
    openDialogCustom: MutableState<Boolean>,
    isErrorDialog: Boolean = false
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(
            openDialogCustom = openDialogCustom,
            isErrorDialog = isErrorDialog
        )
    }
}

//Layout
@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    isErrorDialog: Boolean
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp)
            .testTag(DIALOG_TEST_TAG),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.normal
        ),
        colors = if (isErrorDialog) CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
        ) else CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
        )
    ) {
        //.......................................................................
        Image(
            imageVector = if (isErrorDialog) Icons.Default.Error else Icons.Default.Tsunami,
            contentDescription = null, // decorative
            contentScale = ContentScale.Fit,
            colorFilter = if (isErrorDialog) ColorFilter.tint(
                color = MaterialTheme.colorScheme.error
            ) else ColorFilter.tint(
                color = md_theme_light_onPrimaryContainer
            ),
            modifier = Modifier
                .padding(top = 35.dp)
                .height(70.dp)
                .fillMaxWidth(),

            )

        Column(modifier = Modifier.padding(16.dp)) {
            androidx.compose.material3.Text(
                text = if (isErrorDialog) stringResource(id = string.login_screen_failure_message)
                else stringResource(id = string.login_screen_success_message),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.labelLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        //.......................................................................
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            androidx.compose.material3.TextButton(onClick = {
                openDialogCustom.value = false
            }) {

                androidx.compose.material3.Text(
                    text = stringResource(id = string.login_screen_success_dialog_close),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }
        }

    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun SuccessDialogUIPreview() {
    CustomDialogUI(
        openDialogCustom = mutableStateOf(false),
        isErrorDialog = false
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun FailureDialogUIPreview() {
    CustomDialogUI(
        openDialogCustom = mutableStateOf(false),
        isErrorDialog = true
    )
}