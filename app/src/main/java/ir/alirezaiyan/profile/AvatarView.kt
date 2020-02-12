package ir.alirezaiyan.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class AvatarView : AppCompatImageView {
    private var mBitmap: Bitmap? = null
    private var cornerRadius = 50f
    private val mBitmapPaint = Paint()
    private val mMatrix = Matrix()
    private val mRectF = RectF()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.AvatarView, defStyleAttr, 0)
        cornerRadius = a
            .getDimensionPixelSize(R.styleable.AvatarView_av_radius, cornerRadius.toInt()).toFloat()
        a.recycle()
        init()
    }

    private fun init() {
        mBitmap = getBitmapFromDrawable(drawable)
        val mBitmapShader =
            BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBitmapPaint.shader = mBitmapShader
        mBitmapPaint.isAntiAlias = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (mBitmap == null) {
            return
        }
        mBitmapPaint.shader = BitmapShader(
            resizeBitmap(width, height), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
        mRectF.set(0F, 0F, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(
            mRectF,
            cornerRadius,
            cornerRadius,
            mBitmapPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = cornerRadius.toFloat()
        invalidate()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap: Bitmap = if (drawable is ColorDrawable) {
                Bitmap
                    .createBitmap(width, height, Bitmap.Config.ARGB_8888)
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun resizeBitmap(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mBitmap?.let {
            mMatrix.setScale(width.toFloat() / it.width, height.toFloat() / it.height)
            canvas.drawBitmap(it, mMatrix, null)
        }

        return bitmap
    }
}