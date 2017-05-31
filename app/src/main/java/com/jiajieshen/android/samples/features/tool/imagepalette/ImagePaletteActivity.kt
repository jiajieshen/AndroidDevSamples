package com.jiajieshen.android.samples.features.tool.imagepalette

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.model.ColorSwatch
import com.scausum.imageselector.lib.ImageSelector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import jp.wasabeef.glide.transformations.gpu.*
import kotlinx.android.synthetic.main.activity_image_palette.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class ImagePaletteActivity : AppCompatActivity(), AnkoLogger, Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private val REQUEST_CODE_IMAGE_SELECTOR = 0x100

    lateinit private var adapter: ColorSwatchAdapter
    var imagePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_palette)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener(this)

        recyclerView.setHasFixedSize(true)
        adapter = ColorSwatchAdapter(this, null)
        recyclerView.adapter = adapter

        vgImageContainer.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_image_palette_transform, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE_SELECTOR) {
            val pathList = data?.getStringArrayListExtra(ImageSelector.EXTRA_IMAGE_PATH_LIST)
            if (pathList != null) {
                imagePath = pathList[0]
                handleImageChanged(imagePath, null)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleImageChanged(path: String, transformation: Transformation<Bitmap>?) {
        if (path.isEmpty()) {
            return
        }
        Glide.with(this)
                .load(path)
                .asBitmap()
                .transform(transformation ?: CropTransformation(this))
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.vgImageContainer -> {
                startImageSelectorActivity()
            }
        }
    }

    private fun startImageSelectorActivity() {
        ImageSelector.Builder()
                .setMultipleChoice(false)
                .setMaxSelectedSize(1)
                .setCameraEnable(false)
                .setPreviewEnable(true)
                .build()
                .launch(this@ImagePaletteActivity, REQUEST_CODE_IMAGE_SELECTOR)
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        info("on menu item click")
        if (menuItem?.itemId == R.id.tool_menu_pick_image) {
            startImageSelectorActivity()
        } else {
            val transformation: Transformation<Bitmap> = when (menuItem?.itemId) {
                R.id.tool_menu_transform_gray_scale -> GrayscaleTransformation(this)
                R.id.tool_menu_transform_blur -> BlurTransformation(this)
                R.id.tool_menu_transform_toon -> ToonFilterTransformation(this)
                R.id.tool_menu_transform_sepia -> SepiaFilterTransformation(this)
                R.id.tool_menu_transform_contrast -> ContrastFilterTransformation(this)
                R.id.tool_menu_transform_invert -> InvertFilterTransformation(this)
                R.id.tool_menu_transform_sketch -> SketchFilterTransformation(this)
                R.id.tool_menu_transform_swirl -> SwirlFilterTransformation(this)
                R.id.tool_menu_transform_brightness -> BrightnessFilterTransformation(this)
                R.id.tool_menu_transform_kuwahara -> KuwaharaFilterTransformation(this)
                R.id.tool_menu_transform_vignette -> VignetteFilterTransformation(this)
                else -> CropTransformation(this)
            }
            handleImageChanged(imagePath, transformation)
        }
        return true
    }
}
