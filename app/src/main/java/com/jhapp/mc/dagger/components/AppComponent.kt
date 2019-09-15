package com.jhapp.mc.dagger.components

import com.jhapp.mc.app.App
import com.jhapp.mc.dagger.modules.ApiModule
import com.jhapp.mc.dagger.modules.AppModule
import com.jhapp.mc.dagger.modules.InteractorsModule
import com.jhapp.mc.presentation.business_activity.BusinessActivityViewModel
import com.jhapp.mc.presentation.main_activity.ChatViewModel
import com.jhapp.mc.presentation.main_activity.invest_fragment.InvestFragmentViewModel
import com.jhapp.mc.presentation.my_businesses_activity.MyInvActViewModel
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

    fun inject(obj: ChatViewModel)

    fun inject(obj: InvestFragmentViewModel)

    fun inject(obj: BusinessActivityViewModel)

    fun inject(obj: MyInvActViewModel)
}