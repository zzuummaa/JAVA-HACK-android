package com.jhapp.mc.base.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseSearchAdapter<P>: RecyclerView.Adapter<BaseSearchAdapter.BaseViewHolder<P>>() {

    protected open var mDataList: MutableList<P> = ArrayList()
    protected var mCallback: BaseAdapterCallback<P>? = null


    fun attachCallback(callback: BaseAdapterCallback<P>) {
        this.mCallback = callback
    }

    var hasItems = false


    fun addList(dataList: List<P>) {
        mDataList.addAll(dataList)
        hasItems = true
        notifyDataSetChanged()
    }

    fun addItem(newItem: P) {
        mDataList.add(newItem)
        notifyItemInserted(mDataList.size - 1)
    }

    fun addItemToTop(newItem: P) {
        mDataList.add(0, newItem)
        notifyItemInserted(0)
    }

    fun addListToTop(dataList: List<P>) {
        mDataList.addAll(if (mDataList.isEmpty()) 0 else mDataList.size - 1, dataList)
        notifyDataSetChanged()
    }

    fun clear() {
        mDataList.clear()
        hasItems = false
        notifyDataSetChanged()
    }

    fun updateItems(itemsList: List<P>) {
        mDataList.clear()
        addList(itemsList)
    }

    fun updateItem(position: Int, item: P) {
        mDataList[position] = item
        notifyItemChanged(position)
    }

    fun removeItem(item: P) {
        val p = mDataList.indexOf(item)
        if (p != -1) {
            mDataList.removeAt(p)
            notifyItemRemoved(p)
        }
    }

    fun getData() = mDataList

    override fun onBindViewHolder(holder: BaseViewHolder<P>, position: Int) {
        holder.bind(mDataList[position])

        holder.itemView.setOnClickListener {
            mCallback?.onItemClick(mDataList[position], holder.itemView)
        }
        holder.itemView.setOnLongClickListener {
            if (mCallback == null) {
                false
            } else {
                mCallback!!.onLongClick(mDataList[position], holder.itemView)
            }

        }
    }

    override fun getItemCount(): Int {
        return mDataList.count()
    }

    abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(model: T)
    }

    interface BaseAdapterCallback<T> {
        fun onItemClick(model: T, view: View)
        fun onLongClick(model: T, view: View): Boolean
    }
}

abstract class BaseUpdatesAdapter<T>: BaseSearchAdapter<T>() {

    /**
     * Кол-во кастомных элементов, которые не входят в датасет
     */
    open var supportItemsCount = 0

    private var requestUpdatesListener: OnRequestUpdatesListener? = null

    fun setOnRequestUpdatesListener(l: OnRequestUpdatesListener) {
        this.requestUpdatesListener = l
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        Log.d("BaseUpdatesAdapter", "onBind: $position, dataSize: ${mDataList.size}")
        if (position == mDataList.size - 1)
            requestUpdatesListener?.onCall()

        super.onBindViewHolder(holder, position)
    }

    interface OnRequestUpdatesListener {
        fun onCall()
    }
}