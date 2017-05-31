package com.jiajieshen.android.samples.model

/**
 * Created by sum on 5/14/17.
 */

data class ColorSwatch(var desc: String = "", var color: Int = 0, var population: Int = 0) {
    override fun toString(): String {
        return "ColorSwatch(desc='$desc', color=$color, population=$population)"
    }
}
