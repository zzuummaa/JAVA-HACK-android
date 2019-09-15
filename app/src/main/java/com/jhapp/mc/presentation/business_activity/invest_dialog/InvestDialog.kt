package com.jhapp.mc.presentation.business_activity.invest_dialog

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jhapp.mc.R
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.presentation.business_activity.BusinessActivity
import com.jhapp.mc.presentation.business_activity.BusinessActivityViewModel
import kotlinx.android.synthetic.main.invest_dialog.*
import java.text.DecimalFormat

class InvestDialog: BottomSheetDialogFragment() {

    private val navViewModel by lazy { ViewModelProviders.of(activity as BusinessActivity)[BusinessActivityViewModel::class.java] }

    companion object {
        private const val BUSINESS_EXTRA = "business_extra"
        val instance = {
            business: Business ->
            InvestDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(BUSINESS_EXTRA, business)
                }
            }
        }
    }

        private val business by lazy { arguments!!.getParcelable<Business>(BUSINESS_EXTRA)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyleWithKeyboardSupport)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.invest_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val n = p0.toString().toIntOrNull()
                if (n == null) {
                    tv_profit.text = "RUB"
                    return
                }
                tv_profit.text = "${(n * 1.17).toSimpleDecimalFormat()}   RUB"
            }
        })

        send_button.setOnClickListener {
            navViewModel.invest(et_amount.text.toString().toDoubleOrNull()?:return@setOnClickListener, business)
            this.dismiss()
        }
    }
}

fun Double.toSimpleDecimalFormat(maxFractionDigits: Int = 340): String =
    DecimalFormat("0").apply { maximumFractionDigits = maxFractionDigits }.format(this)