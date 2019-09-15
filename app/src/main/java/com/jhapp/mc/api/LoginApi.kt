package com.jhapp.mc.api

import com.jhapp.mc.api.models.Business
import com.jhapp.mc.api.models.InvestResp
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import java.util.*

const val CONTENT_TYPE = "Content-Type: application/json-patch+json"
interface LoginApi {

    @Headers(CONTENT_TYPE)
    @GET("investments/businesses")
    fun getBusinesses(@Query("category") category: String): Single<List<Business>>

    @Headers(CONTENT_TYPE)
    @GET("investments/businesses")
    fun getAllBusinesses(): Single<List<Business>>

    @Headers(CONTENT_TYPE)
    @POST("/investments/payments")
    fun invest(@Body requestBody: Any): Completable

    @Headers(CONTENT_TYPE)
    @GET("/investments/payments")
    fun getInvestements(): Single<List<InvestResp>>
}