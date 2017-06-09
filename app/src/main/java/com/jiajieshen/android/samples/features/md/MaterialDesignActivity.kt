package com.jiajieshen.android.samples.features.md

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.PopupWindow
import com.jiajieshen.android.samples.R
import io.codetail.animation.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_material_design.*
import kotlinx.android.synthetic.main.layout_md_app_bar.*
import org.jetbrains.anko.toast


class MaterialDesignActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        Toolbar.OnMenuItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener(this)

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
        menuInflater.inflate(R.menu.md_toolbar, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.md_toolbar_settings -> {
                toast("Test")

                val popView = layoutInflater.inflate(R.layout.layout_md_settings, null)
                val popupWindow = PopupWindow(popView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                popupWindow.showAtLocation(toolbar, Gravity.TOP, 0, 0)
                popView.post {
                    val vgSettingsContainer = popView.findViewById(R.id.vgSettingsContainer)
                    vgSettingsContainer.visibility = View.VISIBLE
                    // get the center for the clipping circle
                    val menuView = findViewById(R.id.md_toolbar_settings)
                    val location = IntArray(2)
                    menuView.getLocationOnScreen(location)
                    val cx = location[0] + menuView.width / 2
                    val cy = location[1]

                    // get the final radius for the clipping circle
                    val dx = Math.max(cx, vgSettingsContainer.getWidth() - cx)
                    val dy = Math.max(cy, vgSettingsContainer.getHeight() - cy)
                    val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()

                    // Android native animator
                    val animator = ViewAnimationUtils.createCircularReveal(vgSettingsContainer, cx, cy, 0f, finalRadius)
                    animator.setInterpolator(AccelerateDecelerateInterpolator())
                    animator.setDuration(300)
                    animator.start()
                }


            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.md_drawer_nav_share -> {
                // Handle the share action
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
