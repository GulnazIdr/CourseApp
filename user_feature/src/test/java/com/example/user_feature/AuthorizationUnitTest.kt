package com.example.user_feature

import com.example.user_feature.domain.VerifyEmailUseCaseImpl
import com.example.user_feature.domain.usecases.VerifyEmailUseCase
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AuthorizationUnitTest {
    private lateinit var verifyEmailUseCase: VerifyEmailUseCase

    val emailList = listOf{
        Pair("email@email.com", true)
        Pair("email9@email.com", true)
        Pair("email9@email.ru", true)
        Pair("gulnaz.idrisova.05@bk.ru", true)
        Pair("email", false)
        Pair("email9email.ru", false)
        Pair("123@email.ru", true)
        Pair("email9@gmail12.ru", true)
        Pair("email9@email.ru", true)
        Pair("12.ru", false)
        Pair("email.ru", false)
        Pair("почта", false)
        Pair("почта@почта.ru", false)
        Pair("поч12та@почта.ru", false)
        Pair("", false)
    }

    @Before
    fun setUp(){
        verifyEmailUseCase = VerifyEmailUseCaseImpl()
    }

    @Test
    fun `verify email format text_or_symbol_or_number@text_dot_text of 2 and more symbols`(){
        for (email in emailList){
            assertEquals(email.invoke().second, verifyEmailUseCase.invoke(email.invoke().first))
        }
    }
}