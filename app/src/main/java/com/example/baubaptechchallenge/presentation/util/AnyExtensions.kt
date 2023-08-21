package com.example.baubaptechchallenge.presentation.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T: Any> T.asFlow(): Flow<T> = flow {
    emit(this@asFlow)
}