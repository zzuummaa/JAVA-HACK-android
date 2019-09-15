package com.jhapp.mc.base.adapters

import android.view.View
import android.view.ViewGroup
import com.jhapp.mc.api.models.Chart

data class ChartModel(val chart: Chart? = null, val descr: String? = null)

//class ChartAdapter: BaseSearchAdapter<ChartModel>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChartModel> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    inner class DescriptionViewHolder(v: View): BaseSearchAdapter.BaseViewHolder<ChartModel>(v) {
//
//    }
//}