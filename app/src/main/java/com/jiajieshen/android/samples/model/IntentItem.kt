package com.jiajieshen.android.samples.model

/**
 * Created by sum on 3/29/17.
 */

data class IntentItem(val label: String, val activityClassName: String) {
    override fun toString(): String {
        return "IntentItem(label='$label', activityClassName='$activityClassName')"
    }
}
