package com.jiajieshen.android.samples.features.md.navbar

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jiajieshen.android.samples.R

/**
 * Created by xin on 6/4/17.
 */
class NavigationBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_bar)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.colorAccent)
        }
    }
}