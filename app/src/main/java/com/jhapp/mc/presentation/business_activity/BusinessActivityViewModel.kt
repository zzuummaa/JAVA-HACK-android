package com.jhapp.mc.presentation.business_activity

import androidx.lifecycle.MutableLiveData
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import com.jhapp.mc.interactors.LoginInteractor
import com.jhapp.mc.presentation.business_activity.invest_dialog.toSimpleDecimalFormat
import javax.inject.Inject

class BusinessActivityViewModel: BaseViewModel() {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    val progressLD = MutableLiveData<Boolean>()

    init {
        App.component.inject(this)
    }

    fun invest(amount: Double, business: Business) {
        progressLD.postValue(true)
        compositeDisposable.add(
            loginInteractor
                .invest(amount, business.id)
                .doFinally { progressLD.postValue(false) }
                .subscribe({
                    snackBarLD.postValue("Вы успешно проинвестировали ${
                    amount.toSimpleDecimalFormat()} RUB в ${business.name}")
                }){onError(it)}
        )
    }
}