package com.example.baubaptechchallenge

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.baubaptechchallenge.presentation.login.CONTAINER_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.LOGIN_BUTTON_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.LoginScreen
import com.example.baubaptechchallenge.presentation.login.LoginViewModel
import com.example.baubaptechchallenge.presentation.login.PASSWORD_FIELD_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.USERNAME_FIELD_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.validation.LoginFormState
import com.example.baubaptechchallenge.presentation.util.asFlow
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginTest {

    //Rule definition
    @get:Rule(order = 1)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @BindValue
    @JvmField
    val loginViewModelMock = mockk<LoginViewModel>(relaxed = true)

    //Setup
    @Before
    fun init() {
        hiltTestRule.inject()
    }

    //Tests
    @Test
    fun loginScreen_initialStateComposition() {
        composeTestRule.setContent { 
            LoginScreen(loginViewModel = loginViewModelMock)
        }
        val mockStateFlow = MutableStateFlow(LoginFormState())
        every {
            loginViewModelMock.state
        } returns mockStateFlow

        composeTestRule.onNodeWithTag(CONTAINER_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(USERNAME_FIELD_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PASSWORD_FIELD_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TEST_TAG).assertIsDisplayed()
    }
}