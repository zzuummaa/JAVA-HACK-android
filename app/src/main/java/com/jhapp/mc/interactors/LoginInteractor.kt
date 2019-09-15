package com.jhapp.mc.interactors

import com.jhapp.mc.api.models.Business
import com.jhapp.mc.api.models.Investement
import io.reactivex.Completable
import io.reactivex.Single

interface LoginInteractor {
    fun getBusinesses(category: String?): Single<List<Business>>

    fun invest(amount: Double, businessId: Int): Completable

    fun getInvestements(): Single<List<Investement>>
}