package com.jhapp.mc.interactors.login

import com.jhapp.mc.api.LoginApi
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.api.models.InvestRequestBody
import com.jhapp.mc.api.models.InvestResp
import com.jhapp.mc.api.models.Investement
import com.jhapp.mc.interactors.LoginInteractor
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.function.BiFunction

class LoginInteractorImpl(
    private val loginApi: LoginApi
): LoginInteractor {
    override fun getBusinesses(category: String?): Single<List<Business>> {
        return if (category.isNullOrEmpty())
            loginApi.getAllBusinesses().subscribeOn(Schedulers.io())
        else
            loginApi.getBusinesses(category).subscribeOn(Schedulers.io())
    }

    override fun invest(amount: Double, businessId: Int): Completable {
        return loginApi.invest(InvestRequestBody(amount, businessId)).subscribeOn(Schedulers.io())
    }

    override fun getInvestements(): Single<List<Investement>> {
        return loginApi.getInvestements()
            .zipWith(loginApi.getAllBusinesses(), io.reactivex.functions.BiFunction {
                    t1: List<InvestResp>, t2: List<Business> ->
                t1.map { i -> Investement(i.amount, t2.findLast { it.id == i.business_id }!!) }
            })
            .subscribeOn(Schedulers.io())
    }
}