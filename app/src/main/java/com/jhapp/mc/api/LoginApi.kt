package com.jhapp.mc.api

import io.reactivex.Single
import retrofit2.http.Headers
import retrofit2.http.POST

const val CONTENT_TYPE = "Content-Type: application/json-patch+json"
interface LoginApi {
    @Headers(CONTENT_TYPE)
    @POST("cdacasc")
    fun getInfo(): Single<Any>
}