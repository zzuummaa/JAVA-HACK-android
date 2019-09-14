package com.jhapp.mc.api

import com.jhapp.mc.api.models.Business
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val CONTENT_TYPE = "Content-Type: application/json-patch+json"
interface LoginApi {

    @Headers(CONTENT_TYPE)
    @GET("investments/businesses")
    fun getBusinesses(@Query("category") category: String): Single<List<Business>>

    @Headers(CONTENT_TYPE)
    @GET("investments/businesses")
    fun getAllBusinesses(): Single<List<Business>>
}