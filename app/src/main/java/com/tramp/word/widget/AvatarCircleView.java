package com.tramp.word.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/16
 * version:1.0
 */

public class AvatarCircleView extends ImageView {
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private ColorFilter mColorFilter;
    private int mBorderWidth=DEFAULT_BORDER_WIDTH;
    private int mBorderColor=DEFAULT_BORDER_COLOR;
    private boolean mBorderOverlay;
    private boolean mReady;
    private boolean mSetUpPending;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private float mBorderRadius;
    private float mDrawableRadius;
    private Bitmap mBitmap;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private BitmapShader mBitmapShader;
    private final RectF mDrawableRect = new RectF();
    private final Matrix mShaderMatrix = new Matrix();
    private final RectF mBorderRect = new RectF();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    public AvatarCircleView(Context context) {
        super(context);
        init();
    }

    public AvatarCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.AvatarCircleView,defStyleAttr,0);
        mBorderWidth=a.getDimensionPixelSize(R.styleable.AvatarCircleView_border_width,DEFAULT_BORDER_WIDTH);
        mBorderColor=a.getColor(R.styleable.AvatarCircleView_border_color,DEFAULT_BORDER_COLOR);
        mBorderOverlay=a.getBoolean(R.styleable.AvatarCircleView_border_overlay,DEFAULT_BORDER_OVERLAY);
        a.recycle();
        init();
    }

    private void init(){
        super.setScaleType(SCALE_TYPE);
        mReady=true;
        if(mSetUpPending){
            SetUp();
            mSetUpPending=false;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SetUp();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        if(borderColor==mBorderColor){
            return;
        }
        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    public void setBorderColorResource(@ColorRes int borderColorRes){
        setBorderColor(getContext().getResources().getColor(borderColorRes));
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if(borderWidth==mBorderWidth){
            return;
        }
        mBorderWidth =borderWidth;
        SetUp();
    }

    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if(borderOverlay==mBorderOverlay){
            return;
        }
        mBorderOverlay=borderOverlay;
        SetUp();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap=bm;
        SetUp();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap=getBitmapFromDrawable(drawable);
        SetUp();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap=getBitmapFromDrawable(getDrawable());
        SetUp();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        mBitmap=getBitmapFromDrawable(getDrawable());
        SetUp();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if(cf==mColorFilter){
            return;
        }
        mColorFilter=cf;
        mBitmapPaint.setColorFilter(mColorFilter);
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable){
        if(drawable ==null){
            return null;
        }
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if(drawable instanceof ColorDrawable){
                bitmap=Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,COLORDRAWABLE_DIMENSION,BITMAP_CONFIG);
            }else{
                bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),BITMAP_CONFIG);
            }
            Canvas canvas=new Canvas(bitmap);
            drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }catch (OutOfMemoryError e){
            return null;
        }
    }

    private void SetUp(){
        if(!mReady){
            mSetUpPending=true;
            return;
        }
        if(mBitmap==null){
            return;
        }
        mBitmapShader=new BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBitmapHeight=mBitmap.getHeight();
        mBitmapWidth=mBitmap.getWidth();
        mBorderRect.set(0,0,getWidth(),getHeight());
        mBorderRadius= Math.min((mBorderRect.height()-mBorderWidth)/2,(mBorderRect.width()-mBorderWidth)/2);
        mDrawableRect.set(mBorderRect);
        if(!mBorderOverlay){
            mDrawableRect.inset(mBorderWidth,mBitmapHeight);
        }
        mDrawableRadius=Math.min(mDrawableRect.height()/2,mDrawableRect.width()/2);
        updateShaderMatrix();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable()==null){
            return;
        }
        canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,mBitmapPaint);
        if(mBorderWidth!=0){
            canvas.drawCircle(getWidth()/2,getHeight()/2,mBorderRadius,mBorderPaint);
        }
    }

    private void updateShaderMatrix(){
        float scale;
        float dx = 0;
        float dy = 0;
        mShaderMatrix.set(null);
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
}

































