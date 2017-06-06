package com.jiajieshen.android.samples.features.md

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jiajieshen.android.samples.R

/**
 * Created by xin on 6/6/17.
 */
class ComponentsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_md_components,null)
        return rootView
    }
}