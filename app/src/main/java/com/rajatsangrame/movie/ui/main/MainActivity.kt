package com.rajatsangrame.movie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rajatsangrame.movie.App
import com.rajatsangrame.movie.R
import com.rajatsangrame.movie.adapter.ViewPagerAdapter
import com.rajatsangrame.movie.databinding.ActivityMainBinding
import com.rajatsangrame.movie.di.component.DaggerMainActivityComponent
import com.rajatsangrame.movie.di.component.MainActivityComponent
import com.rajatsangrame.movie.di.module.MainActivityModule
import javax.inject.Inject

/**
 * Created by Rajat Sangrame on 13/6/20.
 * http://github.com/rajatsangrame
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var currentFragment = 0

    @Inject
    lateinit var adapter: ViewPagerAdapter

    //@Inject
    //lateinit var fragmentList: List<Fragment>

    private fun init() {
        binding.navigation.setOnNavigationItemSelectedListener(navigationListener)
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.adapter = adapter
        stackList.add(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getDependency()
        init()
    }

    private fun getDependency() {

        val component: MainActivityComponent = DaggerMainActivityComponent
                .builder().applicationComponent(App.get(this).component)
                .mainActivityModule(MainActivityModule(this))
                .build()
        component.injectMainActivity(this)
    }

    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {

        when (it.itemId) {
            currentFragment -> {
                false
            }
            R.id.navigation_home -> {
                binding.viewPager.setCurrentItem(0, false)
                binding.toolbar.setTitle(R.string.app_name)
                currentFragment = it.itemId
                addToStackList(0)
                true
            }
            R.id.navigation_search -> {
                binding.viewPager.setCurrentItem(1, false)
                binding.toolbar.setTitle(R.string.title_search)
                currentFragment = it.itemId
                addToStackList(1)
                true
            }
            R.id.navigation_saved -> {
                binding.viewPager.setCurrentItem(2, false)
                binding.toolbar.setTitle(R.string.title_saved)
                currentFragment = it.itemId
                addToStackList(2)
                true
            }
            else -> false
        }
    }

    //region Custom stack for fragment transaction
    private val stackList: MutableList<Int> = ArrayList()
    var tabs = intArrayOf(R.id.navigation_home, R.id.navigation_search, R.id.navigation_saved)

    private fun addToStackList(index: Int) {
        if (index == 0) {
            stackList.clear()
            stackList.add(0)
            return
        }
        if (stackList.contains(index)) {
            removeIndex(index)
            stackList.add(index)
            return
        }
        stackList.add(index)
    }

    private fun removeIndex(index: Int) {
        for (i in stackList.indices) {
            if (index == stackList[i]) {
                stackList.removeAt(i)
                return
            }
        }
    }

    private fun handleBackStack() {
        stackList.removeAt(stackList.size - 1)
        val lastIndex = stackList[stackList.size - 1]
        binding.navigation.selectedItemId = tabs[lastIndex]
        if (lastIndex == 0) {
            stackList.clear()
            stackList.add(0)
        }
    }

    override fun onBackPressed() {
        if (stackList.size <= 1) {
            super.onBackPressed()
        }
        handleBackStack()
    }
    //endregion
}