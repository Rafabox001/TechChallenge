package com.example.baubaptechchallenge.usecase

import com.example.baubaptechchallenge.DispatcherRule
import com.example.baubaptechchallenge.domain.repository.LoginRepository
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
import kotlinx.coroutines.flow.collect
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
class LoginUseCaseTest {

    @get:Rule
    val mockRule = DispatcherRule()


    @RelaxedMockK
    private lateinit var loginRepository: LoginRepository

    private lateinit var testObject: LoginUseCase

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testObject = LoginUseCase(loginRepository)
    }

    @Test
    fun `Correct loginCredentials should return LoginSuccess status`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "TestUser",
            password = "Pass123"
        )
        //When
        coEvery {
            loginRepository.attemptLogin(loginCredentials = loginCredentials)
        } returns LoginStatus.LoginSuccess.asFlow()
        testObject.execute(loginCredentials)
        //Then
        verify { loginRepository.attemptLogin(loginCredentials) }
        testScope.launch {
            testObject.execute(loginCredentials).collect { status ->
                assertEquals(
                    expected = LoginStatus.LoginSuccess,
                    actual = status
                )
            }
        }
    }

    @Test
    fun `Empty loginCredentials should return EmptyUserNameAndPassword status`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "",
            password = ""
        )
        //When
        testObject.execute(loginCredentials)
        //Then
        testScope.launch {
            testObject.execute(loginCredentials).collect { status ->
                assertEquals(
                    expected = LoginStatus.EmptyUserNameAndPassword,
                    actual = status
                )
            }
        }
    }

    @Test
    fun `Empty username should return EmptyUserName status`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "",
            password = "password"
        )
        //When
        testObject.execute(loginCredentials)
        //Then
        testScope.launch {
            testObject.execute(loginCredentials).collect { status ->
                assertEquals(
                    expected = LoginStatus.EmptyUserName,
                    actual = status
                )
            }
        }
    }

    @Test
    fun `Empty password should return EmptyPassword status`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "username",
            password = ""
        )
        //When
        testObject.execute(loginCredentials)
        //Then
        testScope.launch {
            testObject.execute(loginCredentials).collect { status ->
                assertEquals(
                    expected = LoginStatus.EmptyPassword,
                    actual = status
                )
            }
        }
    }

    @Test
    fun `Invalid password should return InvalidFormat status`() {
        //Given
        val loginCredentials = LoginCredentials(
            userName = "username",
            password = "123"
        )
        //When
        testObject.execute(loginCredentials)
        //Then
        testScope.launch {
            testObject.execute(loginCredentials).collect { status ->
                assertEquals(
                    expected = LoginStatus.InvalidFormat(6),
                    actual = status
                )
            }
        }
    }
}