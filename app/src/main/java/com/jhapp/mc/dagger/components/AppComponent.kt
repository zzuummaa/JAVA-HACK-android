package com.jhapp.mc.dagger.components

import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import com.jhapp.mc.dagger.modules.ApiModule
import com.jhapp.mc.dagger.modules.AppModule
import com.jhapp.mc.dagger.modules.InteractorsModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    InteractorsModule::class,
    ApiModule::class
])
@Singleton
interface AppComponent {
    fun inject(obj: App)

    fun inject(obj: BaseViewModel)
}