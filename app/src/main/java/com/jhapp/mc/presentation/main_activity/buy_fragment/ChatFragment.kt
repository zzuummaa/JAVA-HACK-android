package com.jhapp.mc.presentation.main_activity.buy_fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jhapp.mc.R
import com.jhapp.mc.presentation.main_activity.MainActivity
import com.jhapp.mc.presentation.main_activity.ChatViewModel

class ChatFragment: Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this)[ChatViewModel::class.java] }

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



        viewModel.startUpdates()
    }
}