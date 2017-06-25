package com.jiajieshen.android.samples.features.tool.imagetransform

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.jiajieshen.android.samples.BuildConfig
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.features.tool.imagepalette.ImagePaletteActivity
import com.scausum.imageselector.lib.ImageSelector
import io.reactivex.Observable
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import jp.wasabeef.glide.transformations.gpu.*
import kotlinx.android.synthetic.main.activity_image_transform.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


/**
 * Created by sum on 6/25/17.
 */
class ImageTransformActivity :
        AppCompatActivity(),
        Toolbar.OnMenuItemClickListener,
        View.OnClickListener {

    companion object {
        val ARG_IMAGE_PATH = "arg_image_path"
    }

    private val REQUEST_CODE_IMAGE_SELECTOR = 0x100
    private var imagePath: String = ""
    private var transformationBtnId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_transform)

        imagePath = intent.getStringExtra(ARG_IMAGE_PATH) ?: ""
        transformationBtnId = R.id.btnNone

        initViews()
        handleImageChanged(imagePath, transformationBtnId)
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener(this)

        vgImageContainer.setOnClickListener(this)
        btnNone.setOnClickListener(this)
        btnGrayscale.setOnClickListener(this)
        btnBlur.setOnClickListener(this)
        btnToon.setOnClickListener(this)
        btnSepia.setOnClickListener(this)
        btnContrast.setOnClickListener(this)
        btnInvert.setOnClickListener(this)
        btnSketch.setOnClickListener(this)
        btnSwirl.setOnClickListener(this)
        btnBrightness.setOnClickListener(this)
        btnKuwahara.setOnClickListener(this)
        btnVignette.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_image_transform, menu)
        return true
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        if (menuItem?.itemId == R.id.tool_menu_pick_image) {
            startImageSelectorActivity()
        } else if (menuItem?.itemId == R.id.tool_menu_save_image) {
            if (TextUtils.isEmpty(imagePath)) {
                toast("No image to save!!!")
            }
            saveImageObservable(imagePath, transformationBtnId)
                    .subscribe { savedPath ->
                        toast("Save image in $savedPath")
                    }
        } else if (menuItem?.itemId == R.id.tool_menu_to_imagepalette) {
            saveImageObservable(imagePath, transformationBtnId)
                    .subscribe { savedPath ->
                        toast("Save image in $savedPath")
                        startImagePalette(savedPath)
                    }
        }
        return true
    }

    override fun onClick(view: View) {
        if (view.id == R.id.vgImageContainer) {
            startImageSelectorActivity()
            return
        }

        // on transform button click
        if (TextUtils.isEmpty(imagePath)) {
            return
        }
        transformationBtnId = view.id
        handleImageChanged(imagePath, transformationBtnId)
    }

    fun getTransformationByButtonId(id: Int) = when (id) {
        R.id.btnGrayscale -> GrayscaleTransformation(this)
        R.id.btnBlur -> BlurTransformation(this)
        R.id.btnToon -> ToonFilterTransformation(this)
        R.id.btnSepia -> SepiaFilterTransformation(this)
        R.id.btnContrast -> ContrastFilterTransformation(this)
        R.id.btnInvert -> InvertFilterTransformation(this)
        R.id.btnSketch -> SketchFilterTransformation(this)
        R.id.btnSwirl -> SwirlFilterTransformation(this)
        R.id.btnBrightness -> BrightnessFilterTransformation(this)
        R.id.btnKuwahara -> KuwaharaFilterTransformation(this)
        R.id.btnVignette -> VignetteFilterTransformation(this)
        else -> CropTransformation(this)
    }

    private fun startImagePalette(image: String) {
        if (TextUtils.isEmpty(image)) {
            toast("No image!!!")
            return
        }
        val intent = Intent(this, ImagePaletteActivity::class.java)
        intent.putExtra(ImagePaletteActivity.ARG_IMAGE_PATH, image)
        startActivity(intent)
    }

    private fun startImageSelectorActivity() {
        ImageSelector.Builder()
                .setMultipleChoice(false)
                .setMaxSelectedSize(1)
                .setCameraEnable(false)
                .setPreviewEnable(true)
                .build()
                .launch(this@ImageTransformActivity, REQUEST_CODE_IMAGE_SELECTOR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE_SELECTOR) {
            val pathList = data?.getStringArrayListExtra(ImageSelector.EXTRA_IMAGE_PATH_LIST)
            if (pathList != null) {
                imagePath = pathList[0]
                transformationBtnId = R.id.btnNone
                handleImageChanged(imagePath, transformationBtnId)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleImageChanged(path: String, transformationBtnId: Int) {
        if (path.isEmpty()) {
            return
        }
        val transformation: Transformation<Bitmap> = getTransformationByButtonId(transformationBtnId)
        Glide.with(this)
                .load(path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(transformation)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                        ivImage.setImageBitmap(resource)
                    }
                })
    }

    private fun saveImageObservable(image: String, transformationBtnId: Int): Observable<String> {
        val transformation: Transformation<Bitmap> = getTransformationByButtonId(transformationBtnId)
        return Observable.create { emitter ->
            Glide.with(this)
                    .load(image)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transform(transformation)
                    .listener(object : RequestListener<String, Bitmap> {
                        override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onException(e: Exception?, model: String?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                            emitter.onError(e)
                            return true
                        }
                    })
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                            val savedPath = saveBitmapToPublicPicturesDir(resource)
                            emitter.onNext(savedPath)
                            emitter.onComplete()
                        }
                    })
        }
    }

    /**
     *
     */
    private fun saveBitmapToPublicPicturesDir(bitmap: Bitmap): String {
        val PUBLIC_PICTURES_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dir = File(PUBLIC_PICTURES_DIR, BuildConfig.IMAGE_DIR)
        dir.mkdirs()
        val file = File(dir, "" + System.currentTimeMillis() + ".png")
        val fos: FileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        return file.absolutePath
    }
}