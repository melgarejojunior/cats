package com.juniormelgarejo.cats.data

import com.juniormelgarejo.cats.data.entity.RequestException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RequestHandler {
    protected fun <T> makeRequest(request: Single<Response<T>>): Single<T> {
        return request.compose(verifyResponseException())
            .compose(verifyRequestException())
            .compose(unwrap())
    }

    protected fun <T> makeCompletableRequest(request: Single<Response<T>>): Completable {
        return request.compose(verifyRequestException())
            .ignoreElement()
    }

    protected fun <T> justVerifyErrors(request: Single<Response<T>>): Completable {
        return request.compose(verifyResponseException())
            .compose(verifyRequestException())
            .ignoreElement()
    }

    private fun <T> unwrap(): SingleTransformer<Response<T>, T> {
        return SingleTransformer { upstream ->
            upstream.map(Response<T>::body)
        }
    }

    private fun <T> verifyRequestException(): SingleTransformer<Response<T>, Response<T>> {
        return SingleTransformer { upstream ->
            upstream.onErrorResumeNext { t ->
                Single.error(
                    when (t) {
                        is RequestException -> t
                        is SocketTimeoutException -> RequestException.TimeoutError
                        is UnknownHostException -> RequestException.UnexpectedError
                        is IOException -> RequestException.NetworkError
                        else -> RequestException.UnexpectedError
                    }
                )
            }
        }
    }

    private fun <T> verifyResponseException(): SingleTransformer<Response<T>, Response<T>> {
        return SingleTransformer { upstream ->
            upstream.doOnSuccess { response ->
                if (!response.isSuccessful) {
                    throw RequestException.HttpError(response.code(), response.errorBody())
                }
            }
        }
    }
}
