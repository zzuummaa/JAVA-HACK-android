package com.jhapp.mc.presentation.main_activity.buy_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhapp.mc.R
import com.jhapp.mc.api.models.ChatMessage
import com.jhapp.mc.base.adapters.BaseSearchAdapter
import com.jhapp.mc.base.adapters.ChatAdapter
import com.jhapp.mc.presentation.business_activity.BusinessActivity
import com.jhapp.mc.presentation.main_activity.MainActivity
import com.jhapp.mc.presentation.main_activity.ChatViewModel
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.invest_dialog.*

class ChatFragment: Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this)[ChatViewModel::class.java] }

    private val adapter by lazy { ChatAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.title = getString(R.string.chat)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initLD.observe(this, Observer {
            adapter.addList(it)
        })

        viewModel.updatesLD.observe(this, Observer {
            adapter.setDataWithDiffResult(it.first)
            it.second.dispatchUpdatesTo(adapter)
        })

        viewModel.scrollLD.observe(this, Observer {
            recycler.scrollToPosition(0)
        })

        adapter.attachCallback(object : BaseSearchAdapter.BaseAdapterCallback<ChatMessage> {
            override fun onItemClick(model: ChatMessage, view: View) {
                try {
                    val n = model.body.substring(model.body.indexOf("//") + 2)
                    viewModel.loadBusiness(n.toInt())
                } catch (e: Exception) {e.printStackTrace()}
            }

            override fun onLongClick(model: ChatMessage, view: View): Boolean {
                return false
            }
        })

        recycler.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                recycler.post {
                    recycler.smoothScrollToPosition(0)
                }
            }
        }

         viewModel.businessLD.observe(this, Observer {
             startActivity(BusinessActivity.instance(it, context!!))
         })

        val linearLayoutManager = LinearLayoutManager(context)
//        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = adapter
        viewModel.startUpdates()

        btn_send.setOnClickListener {
            if (!et_chat.text.isNullOrEmpty()) {
                viewModel.sendMessage(et_chat.text.toString())
                et_chat.setText("")
            }
        }
    }
}