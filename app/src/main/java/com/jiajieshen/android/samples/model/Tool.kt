package com.jiajieshen.android.samples.model

import com.jiajieshen.android.samples.constants.ToolKey

/**
 * Created by sum on 5/14/17.
 */
data class Tool(var key: ToolKey, var name: String) {
    override fun toString(): String {
        return "Tool(key=$key, name='$name')"
    }
}
