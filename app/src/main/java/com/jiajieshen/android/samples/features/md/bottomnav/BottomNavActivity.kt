package com.jiajieshen.android.samples.features.md.bottomnav

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.jiajieshen.android.samples.R
import kotlinx.android.synthetic.main.activity_bottom_nav.*


class BottomNavActivity : AppCompatActivity() {

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                val snackbar = Snackbar.make(vgContentContainer, R.string.title_home, Snackbar.LENGTH_SHORT)
                val vlp: ViewGroup.LayoutParams = snackbar.view.layoutParams
                val flp = FrameLayout.LayoutParams(vlp.width, vlp.height)
                flp.gravity = Gravity.BOTTOM;
                flp.bottomMargin = navigation.height
                snackbar.view.layoutParams = flp
                snackbar.show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        mTextMessage = findViewById(R.id.message) as TextView
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
