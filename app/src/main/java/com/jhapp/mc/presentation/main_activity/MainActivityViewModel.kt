package com.jhapp.mc.presentation.main_activity

import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel: BaseViewModel() {


    init {
        App.component.inject(this)
    }
}