package com.jiajieshen.android.samples.features.md

import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.features.main.IntentActivity
import com.jiajieshen.android.samples.features.md.bottomnav.BottomNavActivity
import com.jiajieshen.android.samples.model.IntentItem
import kotlinx.android.synthetic.main.activity_intent.*
import java.util.*

class MaterialDesignActivity : IntentActivity() {

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.setTitle(R.string.material_design)
    }

    override fun getIntentItems(): List<IntentItem> {
        val intentItemList = ArrayList<IntentItem>()
        intentItemList.add(IntentItem(getString(R.string.bottom_navigation), BottomNavActivity::class.java.name))
        return intentItemList
    }

}
