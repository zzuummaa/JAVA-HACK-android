package com.jhapp.mc.interactors

import com.jhapp.mc.api.models.Business
import io.reactivex.Completable
import io.reactivex.Single

interface LoginInteractor {
    fun getBusinesses(category: String?): Single<List<Business>>
}