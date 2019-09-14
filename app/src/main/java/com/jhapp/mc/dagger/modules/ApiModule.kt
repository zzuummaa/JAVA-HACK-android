package com.jhapp.mc.dagger.modules

import com.jhapp.mc.api.ApiBuilder
import com.jhapp.mc.api.LoginApi
import com.jhapp.mc.api.RxMoshiApiBuilder
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideApiBuilder(): ApiBuilder = RxMoshiApiBuilder()

    @Provides
    fun provideLoginApi(apiBuilder: ApiBuilder): LoginApi
            = apiBuilder.buildApi(LoginApi::class.java)
}