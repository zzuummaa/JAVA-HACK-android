package com.jhapp.mc.presentation.my_businesses_activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jhapp.mc.R
import com.jhapp.mc.base.adapters.MyInvAdapter
import kotlinx.android.synthetic.main.activity_my_investements.*

class MyInvestementsActivity: AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this)[MyInvActViewModel::class.java] }

    private val adapter by lazy { MyInvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_investements)
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.investmentsLD.observe(this, Observer {
            adapter.clear()
            adapter.addList(it)
        })
        recycler.adapter = adapter
        viewModel.refreshData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}