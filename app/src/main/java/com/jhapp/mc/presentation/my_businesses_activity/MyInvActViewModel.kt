package com.jhapp.mc.presentation.my_businesses_activity

import androidx.lifecycle.MutableLiveData
import com.jhapp.mc.api.models.Investement
import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import com.jhapp.mc.interactors.LoginInteractor
import javax.inject.Inject

class MyInvActViewModel: BaseViewModel() {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    val investmentsLD = MutableLiveData<List<Investement>>()

    init {
        App.component.inject(this)
    }

    fun refreshData() {
        compositeDisposable.add(
            loginInteractor.getInvestements()
                .subscribe({
                    investmentsLD.postValue(it)
                }, ::onError)
        )
    }
}