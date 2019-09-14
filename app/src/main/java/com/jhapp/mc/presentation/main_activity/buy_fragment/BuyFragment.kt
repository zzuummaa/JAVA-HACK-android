package com.jhapp.mc.presentation.main_activity.buy_fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.jhapp.mc.R

class BuyFragment: Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.title = getString(R.string.buy_title)
    }
}