package com.jhapp.mc.base.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.jhapp.mc.R
import com.jhapp.mc.api.models.Business
import kotlinx.android.synthetic.main.filters_spinner.view.*
import kotlinx.android.synthetic.main.item_business.view.*

class BusinessesAdapter: BaseUpdatesAdapter<Business?>() {
    companion object {
        private const val HEADER_TYPE = 124
        private const val ROW_TYPE = 0
    }


    override var supportItemsCount = 1

    private var headerCallback: OnSpinnerItemSelected? = null
    private var spinnerSelectedPosition = 0

    fun attachSpinnerCallback(l: OnSpinnerItemSelected) {
        headerCallback = l
    }


    override fun getItemCount() = mDataList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER_TYPE
        } else {
            ROW_TYPE
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Business?>, position: Int) {
        if (position == 0) {
            holder.bind(null)
        } else {
            super.onBindViewHolder(holder, position - supportItemsCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Business?> {
        if (viewType == ROW_TYPE)
            return BusinessViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_business, parent, false))
        else
            return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_adv_header, parent, false))
    }

    private inner class HeaderViewHolder(v: View) : BaseSearchAdapter.BaseViewHolder<Business?>(v) {
        override fun bind(model: Business?) {
            with(itemView) {
                val rootSpinner = filters_spinner
                val adapterSpinner = ArrayAdapter.createFromResource(context, R.array.businesses_filters, R.layout.spinner_items_filter)
                adapterSpinner.setDropDownViewResource(R.layout.spinner_item_filter_opened)
                rootSpinner.adapter = adapterSpinner
                rootSpinner.setSelection(spinnerSelectedPosition)

                rootSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position != spinnerSelectedPosition) {
                            spinnerSelectedPosition = position
                            headerCallback?.onSelected(position)
                        }
                    }
                }
            }
        }
    }

    private inner class BusinessViewHolder(v: View): BaseSearchAdapter.BaseViewHolder<Business?>(v) {
        override fun bind(model: Business?) {
            with(itemView) {
                Glide.with(iv_logo)
                    .load(model?.iconURL)
                    .thumbnail(0.1f)
                    .into(iv_logo)

                tv_name.text = model?.name
                tv_category.text = model?.category
            }
        }
    }

    interface OnSpinnerItemSelected {
        fun onSelected(position: Int)
    }
}