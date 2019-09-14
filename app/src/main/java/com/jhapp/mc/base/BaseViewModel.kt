package com.jhapp.mc.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    val errorLiveData = MutableLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    open fun refresh() {}

    protected fun onError(t: Throwable) {
        t.printStackTrace()
        errorLiveData.postValue(t)
    }
}