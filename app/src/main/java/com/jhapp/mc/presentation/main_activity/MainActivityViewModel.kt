package com.jhapp.mc.presentation.main_activity

import android.util.Log
import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import com.jhapp.mc.interactors.LoginInteractor
import javax.inject.Inject

class MainActivityViewModel: BaseViewModel() {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    init {
        App.component.inject(this)
    }
}