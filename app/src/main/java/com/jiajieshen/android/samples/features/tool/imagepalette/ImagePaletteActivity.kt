package com.jiajieshen.android.samples.features.tool.imagepalette

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.model.ColorSwatch
import com.scausum.imageselector.lib.ImageSelector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_image_palette.*
import java.util.*

class ImagePaletteActivity : AppCompatActivity() {

    private val REQUEST_CODE_IMAGE_SELECTOR = 0x100
    lateinit private var adapter: ColorSwatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_palette)

        initViews()
    }

    private fun initViews() {
        recyclerView.setHasFixedSize(true)
        adapter = ColorSwatchAdapter(this, null)
        recyclerView.adapter = adapter

        vgImageContainer.setOnClickListener {
            // start ImageSelector
            ImageSelector.Builder()
                    .setMultipleChoice(false)
                    .setMaxSelectedSize(1)
                    .setCameraEnable(false)
                    .setPreviewEnable(true)
                    .build()
                    .launch(this@ImagePaletteActivity, REQUEST_CODE_IMAGE_SELECTOR)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE_SELECTOR) {
            val pathList = data.getStringArrayListExtra(ImageSelector.EXTRA_IMAGE_PATH_LIST)
            if (pathList != null) {
                val path = pathList[0]
                handleImageChanged(path)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleImageChanged(path: String) {
        Glide.with(this)
                .load(path)
                .asBitmap()
                .centerCrop()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                        ivImage.setImageBitmap(resource)
                        getPaletteFromBitmap(resource)
                    }
                })
    }

    private fun getPaletteFromBitmap(resource: Bitmap) {
        Observable
                .create<List<ColorSwatch>> { e ->
                    val palette = Palette.Builder(resource).generate()
                    val paletteSwatchList = palette.swatches

                    val colorSwatches = ArrayList<ColorSwatch>(paletteSwatchList.size)
                    for (paletteSwatch in paletteSwatchList) {
                        val item = ColorSwatch()
                        item.desc = "#${Integer.toHexString(paletteSwatch.rgb)} / Population = ${paletteSwatch.population}"
                        item.color = paletteSwatch.rgb
                        item.population = paletteSwatch.population
                        colorSwatches.add(item)
                    }
                    e.onNext(colorSwatches)
                    e.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { colorSwatches ->
                    adapter.items = colorSwatches.sortedBy { -it.population }
                    adapter.notifyDataSetChanged()
                }
    }
}
