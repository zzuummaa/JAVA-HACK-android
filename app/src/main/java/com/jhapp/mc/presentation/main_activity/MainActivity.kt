package com.jhapp.mc.presentation.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jhapp.mc.R
import com.jhapp.mc.presentation.main_activity.buy_fragment.BuyFragment
import com.jhapp.mc.presentation.main_activity.invest_fragment.InvestFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)

        navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_buy -> {
                    replaceFragment(BuyFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                    R.id.menu_invest -> {
                        replaceFragment(InvestFragment())
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> throw IllegalStateException("Cant find fragment")
                }
        }

        replaceFragment(InvestFragment())
    }

    private fun replaceFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, f, f::class.java.name)
            .commit()
    }
}
