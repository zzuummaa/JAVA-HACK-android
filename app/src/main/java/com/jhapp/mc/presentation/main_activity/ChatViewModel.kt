package com.jhapp.mc.presentation.main_activity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.api.models.ChatMessage
import com.jhapp.mc.app.App
import com.jhapp.mc.base.BaseViewModel
import com.jhapp.mc.interactors.LoginInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ChatViewModel: BaseViewModel() {


    @Inject
    lateinit var loginInteractor: LoginInteractor

    private var lastData: List<ChatMessage>? = null

    val initLD = MutableLiveData<List<ChatMessage>>()

    val updatesLD = MutableLiveData<Pair<MutableList<ChatMessage>, DiffUtil.DiffResult>>()

    val businessLD = MutableLiveData<Business>()

    val scrollLD = MutableLiveData<Boolean>()

    init {
        App.component.inject(this)
    }

    fun startUpdates() {
        compositeDisposable.add(
            loginInteractor.subscribeForUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lastData = it
                    updates()
                    initLD.postValue(it)
                }, ::onError)
        )
    }

    private fun updates() {
        compositeDisposable.add(
            loginInteractor.subscribeForUpdates()
                .repeatUntil { false }// :)))))))))))))))))))))))))))))))))))))))))))))
                .subscribe({
                    val dif = lastData?.size != it.size
                    val diff = DiffUtil.calculateDiff(ChatMessageDiffUtils(lastData!!, it))
                    lastData = it
                    updatesLD.postValue(it.toMutableList() to diff)
                    if (dif) scrollLD.postValue(true)
                }, ::onError)
        )
    }

    fun sendMessage(t: String) {
        compositeDisposable.add(
            loginInteractor.sendMessage(t)
                .subscribe({}, ::onError)
        )
    }

    fun loadBusiness(id: Int) {
        compositeDisposable.add(
            loginInteractor.getBusinesses(null)
                .subscribe({
                    val b = it.findLast { it.id == id}?:return@subscribe
                    businessLD.postValue(b!!!!!!)
                }){}
        )
    }
}

class ChatMessageDiffUtils(private val oldList: List<ChatMessage>, private val newList: List<ChatMessage>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val o = oldList[oldItemPosition]
        val n = newList[newItemPosition]
        return o.body == n.body && o.date == n.date
    }
}