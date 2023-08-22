package com.example.baubaptechchallenge.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.baubaptechchallenge.MainActivity
import com.example.baubaptechchallenge.R
import com.example.baubaptechchallenge.presentation.login.CONTAINER_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.DIALOG_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.LOGIN_BUTTON_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.LoginScreen
import com.example.baubaptechchallenge.presentation.login.LoginScreenComponent
import com.example.baubaptechchallenge.presentation.login.LoginViewModel
import com.example.baubaptechchallenge.presentation.login.PASSWORD_ERROR_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.PASSWORD_FIELD_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.USERNAME_ERROR_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.USERNAME_FIELD_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.validation.LoginFormState
import com.example.baubaptechchallenge.presentation.util.UiText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginUseCaseTest {

    //Rule definition
    @get:Rule(order = 1)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    //Setup
    @Before
    fun init() {
        hiltTestRule.inject()
    }

    //Tests
    @Test
    fun loginScreen_initialStateComposition() {
        val currentState = LoginFormState()
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(false)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(false)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(CONTAINER_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(USERNAME_FIELD_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PASSWORD_FIELD_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun loginScreen_successDialogIsShown() {
        val currentState = LoginFormState()
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(true)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(false)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(DIALOG_TEST_TAG).assertIsDisplayed()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.login_screen_success_message))
            .assertExists()
    }

    @Test
    fun loginScreen_failureDialogIsShown() {
        val currentState = LoginFormState()
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(false)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(true)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(DIALOG_TEST_TAG).assertIsDisplayed()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.login_screen_failure_message))
            .assertExists()
    }

    @Test
    fun loginScreen_noUserNameAndPasswordErrorsDisplayed() {
        val currentState = LoginFormState(
            userNameError = UiText.StringResource(R.string.login_screen_validation_username_empty),
            passwordError = UiText.StringResource(R.string.login_screen_validation_password_empty)
        )
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(false)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(false)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(USERNAME_ERROR_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PASSWORD_ERROR_TEST_TAG).assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.login_screen_validation_username_empty)
            )
            .assertExists()
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.login_screen_validation_password_empty)
            )
            .assertExists()
    }

    @Test
    fun loginScreen_noUserNameErrorsDisplayed() {
        val currentState = LoginFormState(
            userNameError = UiText.StringResource(R.string.login_screen_validation_username_empty)
        )
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(false)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(false)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(USERNAME_ERROR_TEST_TAG).assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.login_screen_validation_username_empty)
            )
            .assertExists()
    }

    @Test
    fun loginScreen_noPasswordErrorsDisplayed() {
        val currentState = LoginFormState(
            passwordError = UiText.StringResource(R.string.login_screen_validation_password_empty)
        )
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(false)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(false)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(PASSWORD_ERROR_TEST_TAG).assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.login_screen_validation_password_empty)
            )
            .assertExists()
    }

    @Test
    fun loginScreen_invalidPasswordErrorsDisplayed() {
        val currentState = LoginFormState(
            passwordError = UiText.StringResource(
                R.string.login_screen_validation_password_wrong_format,
                5
            )
        )
        composeTestRule.setContent {
            val isSuccessDialogShown = remember {
                mutableStateOf(false)
            }
            val isFailureDialogShown = remember {
                mutableStateOf(false)
            }
            LoginScreenComponent(
                state = currentState,
                isSuccessDialogShown = isSuccessDialogShown,
                isFailureDialogShown = isFailureDialogShown,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(PASSWORD_ERROR_TEST_TAG).assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.login_screen_validation_password_wrong_format,
                    5
                )
            )
            .assertExists()
    }
}