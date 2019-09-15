package com.jhapp.mc.interactors

import com.jhapp.mc.api.models.Business
import com.jhapp.mc.api.models.ChatMessage
import com.jhapp.mc.api.models.Investement
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface LoginInteractor {
    fun getBusinesses(category: String?): Single<List<Business>>

    fun invest(amount: Double, businessId: Int): Completable

    fun getInvestements(): Single<List<Investement>>

    fun subscribeForUpdates(): Single<List<ChatMessage>>
}