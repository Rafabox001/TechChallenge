package com.example.baubaptechchallenge.login

import com.example.baubaptechchallenge.DispatcherRule
import com.example.baubaptechchallenge.domain.usecase.LoginUseCase
import com.example.baubaptechchallenge.domain.value.LoginCredentials
import com.example.baubaptechchallenge.domain.value.LoginStatus
import com.example.baubaptechchallenge.presentation.login.LoginViewModel
import com.example.baubaptechchallenge.presentation.login.validation.LoginEvent
import com.example.baubaptechchallenge.presentation.login.validation.LoginFormState
import com.example.baubaptechchallenge.presentation.login.validation.LoginValidationEvent
import com.example.baubaptechchallenge.presentation.util.asFlow
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.test.assertEquals
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mockRule = DispatcherRule()


    @RelaxedMockK
    private lateinit var loginUseCase: LoginUseCase

    private lateinit var testObject: LoginViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testObject = LoginViewModel(loginUseCase)
    }

    @Test
    fun `Should return LoginSuccess FormState when Usecase is Successful`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "TestUser",
            password = "Pass123"
        )
        //When
        coEvery {
            loginUseCase.execute(loginCredentials = loginCredentials)
        } returns LoginStatus.LoginSuccess.asFlow()

        assertEquals(
            expected = MutableStateFlow(
                LoginFormState()
            ).value,
            actual = testObject.state.value
        )
        testObject.onEvent(LoginEvent.UserNameChanged("TestUser"))
        testObject.onEvent(LoginEvent.PasswordChanged("Pass123"))
        testObject.onEvent(LoginEvent.Submit)
        //Then
        verify { loginUseCase.execute(loginCredentials) }
        assertEquals(
            expected = MutableStateFlow(
                LoginFormState(
                    userName = "TestUser",
                    password = "Pass123",
                    showLoading = true
                )
            ).value,
            actual = testObject.state.value
        )
        testScope.launch {
            testObject.validationEvents.collectLatest {
                assertEquals(
                    expected = LoginValidationEvent.Success,
                    actual = it
                )
            }
        }
    }

    @Test
    fun `Should return LoginFailure FormState when Usecase is Failure`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "WrongUser",
            password = "Password"
        )
        //When
        coEvery {
            loginUseCase.execute(loginCredentials = loginCredentials)
        } returns LoginStatus.LoginFailure.asFlow()

        assertEquals(
            expected = MutableStateFlow(
                LoginFormState()
            ).value,
            actual = testObject.state.value
        )
        testObject.onEvent(LoginEvent.UserNameChanged("WrongUser"))
        testObject.onEvent(LoginEvent.PasswordChanged("Password"))
        testObject.onEvent(LoginEvent.Submit)
        //Then
        verify { loginUseCase.execute(loginCredentials) }
        assertEquals(
            expected = MutableStateFlow(
                LoginFormState(
                    userName = "WrongUser",
                    password = "Password",
                    showLoading = true
                )
            ).value,
            actual = testObject.state.value
        )
        testScope.launch {
            testObject.validationEvents.collectLatest {
                assertEquals(
                    expected = LoginValidationEvent.Failure,
                    actual = it
                )
            }
        }
    }

    @Test
    fun `Empty LoginCredentials should return EmptyUserNameAndPassword LoginStatus`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "",
            password = ""
        )
        //When
        coEvery {
            loginUseCase.execute(loginCredentials = loginCredentials)
        } returns LoginStatus.EmptyUserNameAndPassword.asFlow()

        assertEquals(
            expected = MutableStateFlow(
                LoginFormState()
            ).value,
            actual = testObject.state.value
        )
        testObject.onEvent(LoginEvent.UserNameChanged(""))
        testObject.onEvent(LoginEvent.PasswordChanged(""))
        testObject.onEvent(LoginEvent.Submit)
        //Then
        verify { loginUseCase.execute(loginCredentials) }
        assertNotNull(testObject.state.value.userNameError)
        assertNotNull(testObject.state.value.passwordError)
        assertNull(testObject.state.value.responseError)
    }

    @Test
    fun `Empty Username should return userNameError State not null`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "",
            password = "password"
        )
        //When
        coEvery {
            loginUseCase.execute(loginCredentials = loginCredentials)
        } returns LoginStatus.EmptyUserName.asFlow()

        assertEquals(
            expected = MutableStateFlow(
                LoginFormState()
            ).value,
            actual = testObject.state.value
        )
        testObject.onEvent(LoginEvent.UserNameChanged(""))
        testObject.onEvent(LoginEvent.PasswordChanged("password"))
        testObject.onEvent(LoginEvent.Submit)
        //Then
        verify { loginUseCase.execute(loginCredentials) }
        assertNotNull(testObject.state.value.userNameError)
        assertNull(testObject.state.value.passwordError)
        assertNull(testObject.state.value.responseError)
    }

    @Test
    fun `Empty Password should return EmptyPassword State not null`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "",
            password = ""
        )
        //When
        coEvery {
            loginUseCase.execute(loginCredentials = loginCredentials)
        } returns LoginStatus.EmptyPassword.asFlow()

        assertEquals(
            expected = MutableStateFlow(
                LoginFormState()
            ).value,
            actual = testObject.state.value
        )
        testObject.onEvent(LoginEvent.UserNameChanged(""))
        testObject.onEvent(LoginEvent.PasswordChanged(""))
        testObject.onEvent(LoginEvent.Submit)
        //Then
        verify { loginUseCase.execute(loginCredentials) }
        assertNull(testObject.state.value.userNameError)
        assertNotNull(testObject.state.value.passwordError)
        assertNull(testObject.state.value.responseError)
    }

    @Test
    fun `Invalid Password should return EmptyUserNameAndPassword LoginStatus`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "Username",
            password = "test"
        )
        //When
        coEvery {
            loginUseCase.execute(loginCredentials = loginCredentials)
        } returns LoginStatus.InvalidFormat(6).asFlow()

        assertEquals(
            expected = MutableStateFlow(
                LoginFormState()
            ).value,
            actual = testObject.state.value
        )
        testObject.onEvent(LoginEvent.UserNameChanged("Username"))
        testObject.onEvent(LoginEvent.PasswordChanged("test"))
        testObject.onEvent(LoginEvent.Submit)
        //Then
        verify { loginUseCase.execute(loginCredentials) }
        assertNull(testObject.state.value.userNameError)
        assertNotNull(testObject.state.value.passwordError)
        assertNull(testObject.state.value.responseError)
    }
}