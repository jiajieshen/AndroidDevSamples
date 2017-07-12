package com.jiajieshen.android.samples.features.main

import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.features.animation.AnimationActivity
import com.jiajieshen.android.samples.features.md.MaterialDesignActivity
import com.jiajieshen.android.samples.features.tool.ToolsActivity
import com.jiajieshen.android.samples.model.IntentItem
import java.util.*


class MainActivity : IntentActivity() {

    override fun getIntentItems(): List<IntentItem> {
        val intentItemList = ArrayList<IntentItem>()
        intentItemList.add(IntentItem(getString(R.string.tools), ToolsActivity::class.java.name))
        intentItemList.add(IntentItem(getString(R.string.material_design), MaterialDesignActivity::class.java.name))
        intentItemList.add(IntentItem(getString(R.string.animation), AnimationActivity::class.java.name))
        return intentItemList
    }

}
