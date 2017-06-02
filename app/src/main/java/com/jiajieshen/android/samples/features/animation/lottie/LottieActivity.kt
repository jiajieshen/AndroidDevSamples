package com.jiajieshen.android.samples.features.animation.lottie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jiajieshen.android.samples.R
import kotlinx.android.synthetic.main.activity_lottie.*

class LottieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        setupViews()
    }

    private fun setupViews() {
        vgLottieButtonContainer.setOnClickListener {
            lottieButtonIcon.playAnimation()
        }
    }
}
