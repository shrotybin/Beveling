package com.example.shortybin.beveling;

import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 自定义斜切的 view
 */
public class PaintView extends View {

    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;// 图形混合模式
    private Context mContext;
    private Bitmap mBitmap;

    public PaintView(Context context) {
        super(context);
        init(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //省略构造方法
    private void init(Context context) {
        mContext = context;
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        // 实例化混合模式
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //新建一个layer,放置在canvas默认layer的上部，产生的layer初始时是完全透明的
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        //dst是先画的图形
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        //设置混合模式
        mPaint.setXfermode(porterDuffXfermode);
        //src是后画的图形
        mPaint.setColor(0xFFFFCC44);

        Path path = new Path();
        path.addRect(-10, mBitmap.getHeight() - 20, mBitmap.getWidth(), mBitmap.getHeight() + 100, Path.Direction.CW);
        Matrix matrix = new Matrix();
        matrix.setRotate(-10);
        path.transform(matrix);

        canvas.drawPath(path, mPaint);
        //还原混合模式
        mPaint.setXfermode(null);
        //或canvas.restore()把这个layer绘制到canvas默认的layer上去
        canvas.restoreToCount(layerId);
    }


}
