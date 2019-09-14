package com.jhapp.mc.dagger.modules

import com.jhapp.mc.api.LoginApi
import com.jhapp.mc.interactors.LoginInteractor
import com.jhapp.mc.interactors.login.LoginInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Provides
    @Singleton
    fun provideLoginInteractor(api: LoginApi): LoginInteractor
            = LoginInteractorImpl(api)
}