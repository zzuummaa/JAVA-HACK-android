package com.jhapp.mc.presentation.main_activity.invest_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jhapp.mc.R
import com.jhapp.mc.api.models.Business
import com.jhapp.mc.base.adapters.BaseSearchAdapter
import com.jhapp.mc.base.adapters.BusinessesAdapter
import com.jhapp.mc.presentation.business_activity.BusinessActivity
import com.jhapp.mc.presentation.main_activity.MainActivity
import com.jhapp.mc.presentation.main_activity.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_invest.*

class InvestFragment: Fragment() {

    private val navViewModel by lazy { ViewModelProviders.of(activity as MainActivity)[MainActivityViewModel::class.java] }
    private val viewModel by lazy { ViewModelProviders.of(this)[InvestFragmentViewModel::class.java] }

    private val adapter by lazy { BusinessesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_invest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listLiveData.observe(this, Observer {
            swipe_refresh.isRefreshing = false
            adapter.clear()
            adapter.addList(it)
        })

        adapter.attachSpinnerCallback(object: BusinessesAdapter.OnSpinnerItemSelected {
            override fun onSelected(position: Int) {
                when (position) {
                    0 -> viewModel.getBusinesses()
                    1 -> viewModel.getBusinesses(Business.CATEGORY_IT)
                    2 -> viewModel.getBusinesses(Business.CATEGORY_ENTERTAINMENT)
                    3 -> viewModel.getBusinesses(Business.CATEGORY_EDUCATION)
                }
            }
        })

        adapter.attachCallback(object : BaseSearchAdapter.BaseAdapterCallback<Business?> {
            override fun onItemClick(model: Business?, view: View) {
                startActivity(BusinessActivity.instance(model!!, context!!))
            }

            override fun onLongClick(model: Business?, view: View): Boolean {
                return false
            }
        })

        viewModel.errorLiveData.observe(this, Observer {
            swipe_refresh.isRefreshing = false
            Snackbar.make(root, it.toString(), Snackbar.LENGTH_LONG).show()
        })


        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }

        recycler.adapter = adapter
        viewModel.refresh()
        swipe_refresh.isRefreshing = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.title = getString(R.string.invest_title)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.investments_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.my_investments) {
            //
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}