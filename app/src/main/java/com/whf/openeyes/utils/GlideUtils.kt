package com.whf.openeyes.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import java.nio.ByteBuffer
import java.security.MessageDigest


private val roundOption = RequestOptions()
        .transform(MultiTransformation(CenterCrop(), RoundedCorners(10)))

private val circleOption = RequestOptions()
        .transform(MultiTransformation(CenterCrop(), CircleCrop()))

fun ImageView.loadSrc(requestManager: RequestManager, url: String) {
    requestManager.load(url).into(this)
}

fun ImageView.loadCircleSrc(requestManager: RequestManager, url: String) {
    requestManager.load(url).apply(circleOption).into(this)
}

fun ImageView.loadRoundSrc(requestManager: RequestManager, url: String) {
    requestManager.load(url).apply(roundOption).into(this)
}

fun View.loadBackgournd(requestManager: RequestManager, url: String) {
    requestManager
            .load(url)
            .into(BackgroundTarget(this))
}

fun View.loadRoundBackground(requestManager: RequestManager, url: String) {
    requestManager
            .load(url)
            .apply(roundOption)
            .into(BackgroundTarget(this))
}

fun View.loadMaskBackground(requestManager: RequestManager,
                            url: String, radius: Int = 0x80) {
    requestManager
            .load(url)
            .apply(RequestOptions().transform(MultiTransformation(
                    CenterCrop(),
                    MaskTransform(radius))))
            .into(BackgroundTarget(this))
}

fun View.loadRoundMaskBackground(requestManager: RequestManager,
                                 url: String, alpha: Int = 0x80,
                                 roundedCorners: Int = 10) {
    requestManager
            .load(url)
            .apply(RequestOptions().transform(MultiTransformation(
                    CenterCrop(),
                    MaskTransform(alpha),
                    RoundedCorners(roundedCorners))))
            .into(BackgroundTarget(this))
}

fun View.loadBlurMaskBackground(context: Context,
                                url: String, radius: Int = 15,
                                alpha: Int = 0x80) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().transform(MultiTransformation(
                    CenterCrop(),
                    GaussianBlurTransform(context, radius, alpha))))
            .into(BackgroundTarget(this))
}

class BackgroundTarget(view: View) : ViewTarget<View, Drawable>(view) {
    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
        getView()?.background = resource
    }
}

class GaussianBlurTransform(
        private val context: Context,
        private val radius: Int,
        private val alpha: Int) : BitmapTransformation() {

    companion object {
        private const val ID: String = "com.whf.openEyes.glide.GaussianBlurTransform"
        private val ID_BYTES = ID.toByteArray()
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap,
                           outWidth: Int, outHeight: Int): Bitmap {

        val result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888)
        val renderScript = RenderScript.create(context)
        val gaussianBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        val allocationOut = Allocation.createFromBitmap(renderScript, result)
        val allocationInt = Allocation.createFromBitmap(renderScript, toTransform)
        gaussianBlur.setRadius(radius.toFloat())
        gaussianBlur.setInput(allocationInt)
        gaussianBlur.forEach(allocationOut)
        allocationOut.copyTo(result)
        renderScript.destroy()

        val canvas = Canvas(result)
        canvas.drawARGB(alpha, 0, 0, 0)

        return result
    }

    override fun hashCode(): Int {
        return ID.hashCode() + radius + alpha
    }

    override fun equals(other: Any?): Boolean {
        return if (other is GaussianBlurTransform) {
            other.radius == radius && other.alpha == alpha
        } else {
            false
        }
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest?) {
        messageDigest?.update(ID_BYTES)

        val radiusData = ByteBuffer.allocate(4).putInt(radius).array()
        messageDigest?.update(radiusData)

        val alphaData = ByteBuffer.allocate(4).putInt(alpha).array()
        messageDigest?.update(alphaData)
    }
}

class MaskTransform(private val alpha: Int) : BitmapTransformation() {

    companion object {
        private const val ID: String = "com.whf.openEyes.glide.MaskTransform"
        private val ID_BYTES = ID.toByteArray()
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap,
                           outWidth: Int, outHeight: Int): Bitmap {
        val result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888)

        val paint = Paint()
        val shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        paint.shader = shader

        val canvas = Canvas(result)
        canvas.drawBitmap(toTransform, 0F, 0F, paint)
        canvas.drawARGB(alpha, 0, 0, 0)
        return result
    }

    override fun hashCode(): Int {
        return ID.hashCode() + alpha
    }

    override fun equals(other: Any?): Boolean {
        return if (other is MaskTransform) {
            other.alpha == alpha
        } else {
            false
        }
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest?) {
        messageDigest?.update(ID_BYTES)

        val radiusData = ByteBuffer.allocate(4).putInt(alpha).array()
        messageDigest?.update(radiusData)
    }
}


