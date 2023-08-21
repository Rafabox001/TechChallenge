package com.example.baubaptechchallenge.presentation

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.baubaptechchallenge.MainActivity
import com.example.baubaptechchallenge.presentation.login.CONTAINER_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.LOGIN_BUTTON_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.LoginScreen
import com.example.baubaptechchallenge.presentation.login.LoginViewModel
import com.example.baubaptechchallenge.presentation.login.PASSWORD_FIELD_TEST_TAG
import com.example.baubaptechchallenge.presentation.login.USERNAME_FIELD_TEST_TAG
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
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    //Setup
    @Before
    fun init() {
        hiltTestRule.inject()
        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                LoginScreen(
                    loginViewModel = composeTestRule.activity.viewModels<LoginViewModel>().value
                )
            }
        }
    }
    
    //Tests
    @Test
    fun loginScreen_initialStateComposition() {
        composeTestRule.onNodeWithTag(CONTAINER_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(USERNAME_FIELD_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PASSWORD_FIELD_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TEST_TAG).assertIsDisplayed()
    }
}