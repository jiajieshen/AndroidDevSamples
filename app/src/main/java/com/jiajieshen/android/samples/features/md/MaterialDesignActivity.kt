package com.jiajieshen.android.samples.features.md

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.jiajieshen.android.samples.R
import kotlinx.android.synthetic.main.activity_material_design.*
import kotlinx.android.synthetic.main.layout_md_app_bar.*

class MaterialDesignActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val snackbar = Snackbar.make(vgContentContainer, "Replace with your own action", Snackbar.LENGTH_SHORT)
            val vlp: ViewGroup.LayoutParams = snackbar.view.layoutParams
            val flp = CoordinatorLayout.LayoutParams(vlp.width, vlp.height)
            flp.anchorGravity = Gravity.BOTTOM
            flp.anchorId = R.id.vgContentContainer
            flp.bottomMargin = view.height
            snackbar.view.layoutParams = flp
            snackbar.setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawerNavView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.md_drawer_nav_share -> {
                // Handle the camera action
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
