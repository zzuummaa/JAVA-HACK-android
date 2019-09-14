package com.jhapp.mc.interactors.login

import com.jhapp.mc.api.LoginApi
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.interactors.LoginInteractor
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LoginInteractorImpl(
    private val loginApi: LoginApi
): LoginInteractor {
    override fun getBusinesses(category: String?): Single<List<Business>> {
        return if (category.isNullOrEmpty())
            loginApi.getAllBusinesses().subscribeOn(Schedulers.io())
        else
            loginApi.getBusinesses(category).subscribeOn(Schedulers.io())
    }
}