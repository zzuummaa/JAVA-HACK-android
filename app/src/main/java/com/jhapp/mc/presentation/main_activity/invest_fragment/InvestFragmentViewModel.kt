package com.jhapp.mc.presentation.main_activity.invest_fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import com.jhapp.mc.interactors.LoginInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class InvestFragmentViewModel: BaseViewModel() {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    val listLiveData = MutableLiveData<List<Business>>()

    init {
        App.component.inject(this)
    }

    private var currentCat: String? = null

    override fun refresh() {
        super.refresh()
        getBusinesses(currentCat)
    }

    fun getBusinesses(category: String? = null) {
        currentCat = category
        compositeDisposable.add(
            loginInteractor.getBusinesses(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("vdsvdsv", it.toString())
                    listLiveData.postValue(it)
                }){onError(it)}
        )
    }
}