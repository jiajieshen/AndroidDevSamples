package com.jiajieshen.android.samples.features.main

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jiajieshen.android.library.adapterdelegate.BaseAdapterDelegate
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.model.IntentItem

import com.scausum.adapterdelegate.DelegationAdapter
import com.scausum.adapterdelegate.OnDelegateClickListener

/**
 * Created by sum on 3/29/17.
 */

class IntentAdapter(private val activity: Activity, items: List<IntentItem>?)
    : DelegationAdapter<IntentItem>(), OnDelegateClickListener {

    init {
        this.items = items

        val mainDelegate = IntentDelegate(activity)
        mainDelegate.onDelegateClickListener = this
        this.addDelegate(mainDelegate)
    }

    override fun onClick(view: View, position: Int) {
        if (position !in 0 until itemCount) {
            return
        }
        when (view.id) {
            R.id.vg_intent_item_container -> {
                val intent = Intent()
                intent.setClassName(activity, items[position].activityClassName)
                activity.startActivity(intent)
            }
        }
    }

    /**
     *
     */
    private class IntentDelegate(activity: Activity)
        : BaseAdapterDelegate<IntentItem>(activity, R.layout.item_intent) {

        override fun isForViewType(item: IntentItem): Boolean {
            return true
        }

        override fun onCreateViewHolder(itemView: View): RecyclerView.ViewHolder {
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: IntentItem, payloads: List<Any>) {
            val viewHolder = holder as ViewHolder
            viewHolder.tvLabel.text = item.label
        }
    }

    /**
     *
     */
    private class ViewHolder constructor(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val tvLabel = itemView.findViewById(R.id.tv_intent_item_label) as TextView

    }
}
