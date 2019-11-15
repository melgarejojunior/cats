package com.juniormelgarejo.cats.data.entity

import okhttp3.ResponseBody

sealed class RequestException(val errorMessage: String) : Exception() {
    object TimeoutError : RequestException("Timeout")
    object UnexpectedError : RequestException("Unexpected")
    object NetworkError : RequestException("Network")
    class HttpError(code: Int, body: ResponseBody? = null) : RequestException("$code - ${body?.string()}")
    class GenericError(error: String) : RequestException(error)
}