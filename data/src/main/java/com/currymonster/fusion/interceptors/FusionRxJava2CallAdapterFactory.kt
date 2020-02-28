package com.currymonster.fusion.interceptors

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class FusionRxJava2CallAdapterFactory private constructor(
    private val context: Context
) : CallAdapter.Factory() {

    private val original by lazy {
        RxJava2CallAdapterFactory.create()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(context, wrapped)
    }

    private class RxCallAdapterWrapper<R>(
        private val context: Context,
        private val wrapped: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type =
            wrapped.responseType()

        override fun adapt(call: Call<R>): Any =
            wrapped.adapt(call).let {
                when (it) {
                    is Completable ->
                        it.onErrorResumeNext { throwable ->
                            Completable.error(
                                wrapException(
                                    throwable,
                                    call
                                )
                            )
                        }
                    is Single<*> ->
                        it.onErrorResumeNext { throwable: Throwable ->
                            Single.error(wrapException(throwable, call))
                        }
                    is Observable<*> ->
                        it.onErrorResumeNext { throwable: Throwable ->
                            Observable.error(wrapException(throwable, call))
                        }
                    is Maybe<*> ->
                        it.onErrorResumeNext { throwable: Throwable ->
                            Maybe.error(wrapException(throwable, call))
                        }
                    is Flowable<*> ->
                        it.onErrorResumeNext { throwable: Throwable ->
                            Flowable.error(wrapException(throwable, call))
                        }
                    else -> throw RuntimeException("Rx Type not supported")
                }
            }

        private fun wrapException(throwable: Throwable, call: Call<R>): Throwable {
            return when (throwable) {
                is HttpException -> {
                    wrapHttpException(throwable, call)
                }
                is IOException -> {
                    wrapNetworkError(context, throwable)
                }
                else -> {
                    throwable
                }
            }
        }

        private fun wrapHttpException(httpException: HttpException, call: Call<R>): Throwable {
            return httpException.response()?.let { response ->
                response.errorBody()?.string()?.let { responseBody ->
                    try {
                        /*
                         * Try parsing as JSON, if that fails we use the raw response body.
                         * Server sometimes sends just a string as an error, but still sets content type
                         * to JSON
                         */
                        val body = Gson().fromJson(responseBody, ErrorResponse::class.java)
                        if (body?.error?.code != null) {
                            ServerException(
                                body.error.code,
                                body.error.description,
                                body.error.field,
                                httpException
                            )
                        } else {
                            httpException
                        }
                    } catch (t: JsonSyntaxException) {
                        httpException
                    } catch (t: Throwable) {
                        httpException
                    }
                }
            } ?: httpException
        }

        private fun wrapNetworkError(context: Context, throwable: Throwable): Throwable =
            NetworkException(
                context.getString(com.currymonster.fusion.data.R.string.error_generic_title),
                context.getString(com.currymonster.fusion.data.R.string.error_network_generic),
                throwable
            )
    }

    companion object {

        const val VALIDATION_ERROR = "VALIDATION_ERROR"
        const val BUSINESS_NOT_FOUND = "BUSINESS_NOT_FOUND"

        fun create(context: Context): CallAdapter.Factory =
            FusionRxJava2CallAdapterFactory(context)
    }

    data class ErrorResponse(
        val error: Error?
    ) {

        data class Error(
            val code: String,
            val description: String,
            val field: String?
        )
    }

    class ServerException(
        val status: String,
        val description: String,
        val field: String?,
        val throwable: Throwable?
    ) : Exception(description, throwable)

    class NetworkException(
        val title: String,
        val description: String,
        val throwable: Throwable?
    ) : Exception(description, throwable)
}