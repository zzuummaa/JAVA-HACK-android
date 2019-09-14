package com.jhapp.mc.app

import android.app.Application
import com.jhapp.mc.dagger.components.AppComponent
import com.jhapp.mc.dagger.components.DaggerAppComponent
import com.jhapp.mc.dagger.modules.ApiModule
import com.jhapp.mc.dagger.modules.AppModule
import com.jhapp.mc.dagger.modules.InteractorsModule

class App: Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
        component.inject(this)
    }

    private fun buildComponent() = DaggerAppComponent.builder()
        .appModule(AppModule(applicationContext))
//        .databaseModule(DatabaseModule())
        .apiModule(ApiModule())
        .interactorsModule(InteractorsModule())
        .build()
}