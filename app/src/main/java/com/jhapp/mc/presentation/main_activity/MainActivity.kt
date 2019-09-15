package com.jhapp.mc.presentation.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jhapp.mc.R
import com.jhapp.mc.presentation.main_activity.buy_fragment.ChatFragment
import com.jhapp.mc.presentation.main_activity.invest_fragment.InvestFragment
import com.jhapp.mc.presentation.my_businesses_activity.MyInvestementsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)[ChatViewModel::class.java]
    }

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)

        navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_buy -> {
                    menu?.setGroupVisible(0, false)
                    replaceFragment(ChatFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                    R.id.menu_invest -> {
                        menu?.setGroupVisible(0, true)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menu?.clear()
        menuInflater.inflate(R.menu.investments_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.my_investments) {
            startActivity(Intent(this, MyInvestementsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
