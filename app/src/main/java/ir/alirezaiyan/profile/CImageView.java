package ir.alirezaiyan.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;

public class CImageView extends ImageView {


    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private float cornerRadius = 50;
    private final Paint mBitmapPaint = new Paint();

    public CImageView(Context context) {
        super(context);
        init();
    }

    public CImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);

        cornerRadius = a
                .getDimensionPixelSize(R.styleable.CircleImageView_civ_radius, (int) cornerRadius);

        a.recycle();

        init();
    }

    private void init(){
        mBitmap = getBitmapFromDrawable(getDrawable());


        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);
        mBitmapPaint.setAntiAlias(true);

    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        if (mBitmap == null) {
            return;
        }

        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), cornerRadius, cornerRadius, mBitmapPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap
                        .createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
