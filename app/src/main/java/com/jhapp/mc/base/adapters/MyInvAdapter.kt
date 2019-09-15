package com.jhapp.mc.base.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jhapp.mc.R
import com.jhapp.mc.api.models.Investement
import com.jhapp.mc.presentation.business_activity.invest_dialog.toSimpleDecimalFormat
import kotlinx.android.synthetic.main.item_business.view.*

class MyInvAdapter: BaseSearchAdapter<Investement>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Investement> {
        return InvestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_business, parent, false))
    }

    private inner class InvestViewHolder(v: View): BaseSearchAdapter.BaseViewHolder<Investement>(v) {
        override fun bind(model: Investement) {
            with(itemView) {
                tv_amount.visibility = View.VISIBLE
                tv_amount.text = model.amount.toSimpleDecimalFormat() + " RUB"
                tv_name.text = model.business.name
                Glide.with(iv_logo)
                    .load(model.business.iconURL)
                    .thumbnail(0.1f)
                    .into(iv_logo)
                tv_category.text = model.business.category
            }
        }
    }
}