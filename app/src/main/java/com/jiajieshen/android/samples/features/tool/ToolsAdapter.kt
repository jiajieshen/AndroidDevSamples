package com.jiajieshen.android.samples.features.tool

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jiajieshen.android.library.adapterdelegate.BaseAdapterDelegate
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.model.Tool
import com.scausum.adapterdelegate.DelegationAdapter
import com.scausum.adapterdelegate.OnDelegateClickListener

/**
 * Created by sum on 5/14/17.
 */
class ToolsAdapter(private val activity: Activity, items: List<Tool>?
) : DelegationAdapter<Tool>(), OnDelegateClickListener {

    init {
        this.items = items

        val toolDelegate = ToolDelegate(activity)
        toolDelegate.onDelegateClickListener = this
        this.addDelegate(toolDelegate)
    }

    override fun onClick(view: View, position: Int) {
        if (position !in 0 until itemCount) {
            return
        }
        if (view.id != R.id.vg_tools_tool_item_container) {
            return
        }
        val tool = items[position]
        val intent = Intent()
        intent.setClassName(activity, tool.activityClassName)
        activity.startActivity(intent)
    }

    /**
     *
     */
    private class ToolDelegate(activity: Activity)
        : BaseAdapterDelegate<Tool>(activity, R.layout.item_tools_tool) {

        override fun onCreateViewHolder(itemView: View): RecyclerView.ViewHolder {
            return ViewHolder(itemView)
        }

        override fun isForViewType(item: Tool): Boolean {
            return true
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Tool, payloads: List<Any>) {
            val viewHolder = holder as ViewHolder
            viewHolder.tvName.text = item.name
            Glide.with(activity)
                    .load(item.iconRes)
                    .centerCrop()
                    .into(viewHolder.ivIcon)
        }
    }

    /**
     *
     */
    private class ViewHolder internal constructor(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val ivIcon: ImageView = itemView.findViewById(R.id.iv_tools_tool_icon) as ImageView
        val tvName: TextView = itemView.findViewById(R.id.tv_tools_tool_name) as TextView

    }
}
