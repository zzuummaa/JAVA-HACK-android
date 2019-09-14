package com.jhapp.mc.api

import android.util.Log
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

interface ApiBuilder {
    fun <T> buildApi(api: Class<T>): T
}


class RxMoshiApiBuilder(url: String = "") : ApiBuilder {

    private val retrofit: Retrofit

    val nullOnEmptyConverterFactory = object : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
            val jsonParser = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter<Any?>(
                type)

            override fun convert(value: ResponseBody): Any? {
                val str = value.string()
                val errorAdapter = Moshi.Builder().add(
                    KotlinJsonAdapterFactory()).build().adapter<ActionApiError>(
                    ActionApiError::class.java)

                return try {
                    if (value.contentLength() == 0L) {
                        Log.d("Api", "Empty response")
                        return Empty()
                    }
                    val error = errorAdapter.lenient().fromJson(str) ?: throw NullPointerException()
                    throw Exception(error.toString())
                } catch (e: NullPointerException) {
                    Log.d("Api", "NullPointerException: data: $str")
                    jsonParser.lenient().fromJson(str) ?: throw NullPointerException()
                } catch (e: JsonDataException) {
                    Log.d("Api", "JsonDataException: data: $str")
                    jsonParser.lenient().fromJson(str) ?: throw NullPointerException()
                }
            }
        }
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor {
                val request = it.request()
                val requestWithHeader = request.newBuilder().header("Device-Mobile", "Mobile").build()
                it.proceed(requestWithHeader)
            }

        httpClient.addInterceptor(logging)
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .client(httpClient.build())
            .build()
    }

    override fun <T> buildApi(api: Class<T>) = retrofit.create(api)

}

class Empty
data class ActionApiError(val error: Exception)
