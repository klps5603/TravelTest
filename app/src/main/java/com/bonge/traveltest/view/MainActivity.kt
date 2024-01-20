package com.bonge.traveltest.view

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bonge.traveltest.R
import com.bonge.traveltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val fragmentList = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setSupportActionBar(binding.toolBar)
        replace(TravelFragment())

        binding.toolBar.setNavigationOnClickListener {
            pop()
        }
    }

    fun setTitle(title: String) {
        binding.toolBar.title = title
    }

    fun replace(fragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach {
            beginTransaction.remove(it)
        }
        fragmentList.clear()
        fragmentList.add(fragment)
        beginTransaction
            .add(R.id.frame_layout, fragment)
            .show(fragment)
            .commitAllowingStateLoss()
    }


    fun push(fragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.setCustomAnimations(
            R.anim.slide_right_in,
            R.anim.slide_left_out,
            R.anim.slide_left_in,
            R.anim.slide_right_out
        )
        if (fragmentList.size != 0) {
            beginTransaction.remove(fragmentList.last())
        }
        fragmentList.add(fragment)
        beginTransaction
            .add(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .show(fragment)
            .commitAllowingStateLoss()
        setToolbarNavigationIcon()
    }

    fun pop() {
        if (fragmentList.size == 1) {
            finish()
            return
        }
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.setCustomAnimations(
            R.anim.slide_left_in,
            R.anim.slide_right_out,
            R.anim.slide_right_in,
            R.anim.slide_left_out
        )
        val fragment = fragmentList.removeAt(fragmentList.size - 1)
        beginTransaction.show(fragment).commitAllowingStateLoss()
        supportFragmentManager.popBackStackImmediate()
        setToolbarNavigationIcon()
    }

    private fun setToolbarNavigationIcon() {
        if (fragmentList.size > 1) {
            val typedValue = TypedValue().also {
                theme.resolveAttribute(
                    android.R.attr.homeAsUpIndicator,
                    it,
                    true
                )
            }
            binding.toolBar.navigationIcon = ContextCompat.getDrawable(this, typedValue.resourceId)
        } else {
            binding.toolBar.navigationIcon = null
        }
    }

    override fun onBackPressed() {
        pop()
    }

}