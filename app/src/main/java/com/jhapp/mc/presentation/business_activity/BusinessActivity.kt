package com.jhapp.mc.presentation.business_activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.jhapp.mc.R
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.presentation.business_activity.ChartFragment.ChartFragment
import com.jhapp.mc.presentation.business_activity.invest_dialog.InvestDialog
import kotlinx.android.synthetic.main.activity_invest.*
import kotlin.math.abs

class BusinessActivity: AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[BusinessActivityViewModel::class.java] }

    companion object {
        private const val B_EXTRA = "b_extra"
        val instance = {
            b: Business, ctx: Context ->
            Intent(ctx, BusinessActivity::class.java).apply {
                putExtra(B_EXTRA, b)
            }
        }
    }

    private var defOffset = 232

    private lateinit var business: Business

    private val progress by lazy {
        val p = ProgressDialog(this)
        p.setMessage("Отправка запроса")
        p.setCancelable(false)
        p
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invest)
        setSupportActionBar(toolbar as Toolbar)
        business = intent.getParcelableExtra(B_EXTRA)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
        supportActionBar?.title = business.name
        defOffset = appbar_actual.totalScrollRange

        Glide.with(this)
            .load(business.iconURL)
            .into(iv_collaps)

        appbar_actual.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->

            if (abs(abs(p1) - p0.totalScrollRange) in ((-1)..1)) {
                collapsing_toolbar_actual?.title = business.name
                //  Collapsed

            } else {
                //Expanded
                collapsing_toolbar_actual?.title = ""
            }
        })

        tv1.text = business.descriptionText

        replaceFragment(ChartFragment.instance(business.profit, getString(R.string.profit), 0), R.id.container1)
        replaceFragment(ChartFragment.instance(business.revenue, getString(R.string.revenue), 2), R.id.container2)
        replaceFragment(ChartFragment.instance(business.debts, getString(R.string.debts), 1), R.id.container3)
        replaceFragment(ChartFragment.instance(business.assets, getString(R.string.assets), 2), R.id.container4)
        replaceFragment(ChartFragment.instance(business.capital, getString(R.string.capital), 3), R.id.container5)

        btn_invest.setOnClickListener {
            InvestDialog.instance(business).show(supportFragmentManager, "")
        }

        viewModel.errorLiveData.observe(this, Observer {
            Snackbar.make(root, it.toString(), Snackbar.LENGTH_LONG).show()
            progress.hide()
        })

        viewModel.progressLD.observe(this, Observer {
            if (it) progress.show()
            else progress.hide()
        })

        viewModel.snackBarLD.observe(this, Observer {
            Snackbar.make(root, it.toString(), Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(f: Fragment, container: Int) =
        supportFragmentManager.beginTransaction()
            .replace(container, f, f::class.java.name)
            .commit()
}
