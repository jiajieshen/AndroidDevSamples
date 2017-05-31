package com.jiajieshen.android.samples.features.tool.imagepalette

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jiajieshen.android.library.adapterdelegate.BaseAdapterDelegate
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.model.ColorSwatch
import com.scausum.adapterdelegate.DelegationAdapter
import java.util.*

/**
 * Created by sum on 5/14/17.
 */

class ColorSwatchAdapter(private val activity: Activity, items: List<ColorSwatch>?
) : DelegationAdapter<ColorSwatch>() {

    init {
        this.items = items ?: ArrayList<ColorSwatch>(16)

        val swatchDelegate = ColorSwatchDelegate(activity)
        this.addDelegate(swatchDelegate)
    }

    /**
     *
     */
    private class ColorSwatchDelegate(activity: Activity)
        : BaseAdapterDelegate<ColorSwatch>(activity, R.layout.item_tool_color_swatch) {

        override fun onCreateViewHolder(itemView: View): RecyclerView.ViewHolder {
            return ViewHolder(itemView)
        }

        override fun isForViewType(item: ColorSwatch): Boolean {
            return true
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ColorSwatch, payloads: List<Any>) {
            val viewHolder = holder as ViewHolder
            viewHolder.tvDesc.text = item.desc
            viewHolder.tvDesc.setBackgroundColor(item.color)
        }
    }

    /**
     *
     */
    private class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDesc: TextView = itemView.findViewById(R.id.tv_tool_color_swatch_desc) as TextView
    }
}
