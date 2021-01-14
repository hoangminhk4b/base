package com.base.createbase.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import app.base.common.base.viewbinding.BaseActivity
import com.base.createbase.R
import com.base.createbase.databinding.ActivityNavigationBinding
import com.core.common.base.setupWithNavController

// File NavigationActivity
// @project Create Base
// @author minhhoang on 14-01-2021
class NavigationActivity : BaseActivity() {
    private var currentNavController: LiveData<NavController>? = null
    override fun getLayoutID(): Int {
        return R.layout.activity_navigation
    }

    override fun initView() {
        setupBottomNavigationBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     *
     */
    private fun setupBottomNavigationBar() {
        //todo
        // Khi sử dụng navigation phải mở actionbar, tức trong theme phải mở action bar, nếu muốn ẩn thì phải supportActionBar?.hide()
        // id của navigation phải giống với id của bottom nav menu
        // navigation phải set startDestination
        val bottomNavigationView = getBinding<ActivityNavigationBinding>().navView

        val navGraphIds = listOf(R.navigation.home,
            R.navigation.dashboard,
            R.navigation.notifications)
        supportActionBar?.hide()

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}