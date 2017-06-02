package com.jiajieshen.android.samples.features.animation

import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.features.animation.lottie.LottieActivity
import com.jiajieshen.android.samples.features.main.IntentActivity
import com.jiajieshen.android.samples.model.IntentItem
import kotlinx.android.synthetic.main.activity_intent.*
import java.util.*

class AnimationActivity : IntentActivity() {

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.setTitle(R.string.animation)
    }

    override fun getIntentItems(): List<IntentItem> {
        val intentItemList = ArrayList<IntentItem>()
        intentItemList.add(IntentItem(getString(R.string.lottie), LottieActivity::class.java.name))
        return intentItemList
    }

}
