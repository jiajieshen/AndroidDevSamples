package com.jiajieshen.android.library.adapterdelegate

import android.app.Activity
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.scausum.adapterdelegate.AdapterDelegate
import com.scausum.adapterdelegate.OnDelegateClickListener

abstract class BaseAdapterDelegate<T>(
        protected val activity: Activity,
        @LayoutRes protected val layoutRes: Int
) : AdapterDelegate<T>(activity) {

    var onDelegateClickListener: OnDelegateClickListener? = null

    override fun onCreateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        val itemView = layoutInflater.inflate(layoutRes, parent, false)
        val viewHolder = onCreateViewHolder(itemView)
        itemView.setOnClickListener({ v ->
            val position = viewHolder.adapterPosition
            onDelegateClickListener?.onClick(v, position)
        })
        return viewHolder
    }

    protected abstract fun onCreateViewHolder(itemView: View): RecyclerView.ViewHolder
}
