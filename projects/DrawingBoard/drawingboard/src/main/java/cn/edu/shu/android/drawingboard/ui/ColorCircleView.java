package cn.edu.shu.android.drawingboard.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by otakuplus on 14-2-22.
 */
public class ColorCircleView extends View {

    private Paint paint = null;
    private Paint centerPaint = null;
    private Paint pickerCirclePaint = null;

    private int[] circleColors = null;
    private int[] rectColors = null;

    private float radius;
    private RectF rect = null;
    private float rectXLeft, rectYLeft, rectXRight, rectYRight;
    private float pickerX, pickerY;
    boolean isTouch = false;

    private int colorA, colorR, colorG, colorB;

    public ColorCircleView(Context context) {
        super(context);
        init();
    }

    public ColorCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 定义环状颜色列表
        circleColors = new int[]{
                0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                0xFFFFFF00, 0xFFFF0000
        };
        // 3d图像处理概念当中的渲染器，产生扫描线。前两个是参数是中心坐标，接下来是颜色列表（起始颜色到结束颜色），最后一个参数是颜色坐标
        // 一般设置为null。
        Shader shader = new SweepGradient(0, 0, circleColors, null);
        // 设置抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画笔设置为实心
        paint.setStyle(Paint.Style.STROKE);
        paint.setShader(shader);
        paint.setStrokeWidth(36);

        // 初始化正方形选择区颜色
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setStrokeWidth(36);
        // 画笔设置为填充
        centerPaint.setStyle(Paint.Style.FILL);

        pickerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pickerCirclePaint.setColor(Color.WHITE);
        pickerCirclePaint.setStyle(Paint.Style.STROKE);
        pickerCirclePaint.setStrokeWidth(10);

        colorA = 255;
        colorR = 255;
        colorG = 255;
        colorB = 255;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // 计算圆环半径
        radius = (width - paint.getStrokeWidth()) * 0.35f;

        rectXLeft = width * 0.3f;
        rectYLeft = height * 0.3f;
        rectXRight = width * 0.7f;
        rectYRight = height * 0.7f;

        if (rect == null) {
            rect = new RectF(-radius, -radius, radius, radius);
        }

        rectColors = null;
        rectColors = new int[]{
                Color.WHITE,
                Color.RED,
        };
        Shader shader1 = new LinearGradient(rectXLeft, rectYLeft, rectXLeft, rectYRight, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
        Shader shader2 = new LinearGradient(rectXLeft, rectYLeft, rectXRight, rectYLeft, Color.WHITE, Color.RED, Shader.TileMode.CLAMP);
        ComposeShader shader3 = new ComposeShader(shader1, shader2, PorterDuff.Mode.MULTIPLY);
        centerPaint.setShader(shader3);
        canvas.translate(0, 0);
        canvas.drawRect(rectXLeft, rectYLeft, rectXRight, rectYRight, centerPaint);

        // 绘制彩色圆环
        canvas.translate(width / 2, height / 2);
        canvas.drawOval(rect, paint);

        if (isTouch) {
            canvas.translate(-width / 2, -height / 2);
            canvas.drawCircle(pickerX, pickerY, 20, pickerCirclePaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        if (inRect(touchX, touchY) || inCircle(touchX, touchY)) {
            isTouch = true;
            pickerX = touchX;
            pickerY = touchY;
        }
        invalidate();

        return super.onTouchEvent(event);
    }

    public int getPickedColor() {
        return Color.argb(colorA, colorR, colorG, colorB);
    }

    private void interCircle() {

    }

    private void interRect() {
        
    }

    /**
     * 判断某个点是否在方形区域内
     *
     * @param pointX 点的X坐标
     * @param pointY 点的Y坐标
     * @return boolean值，说明点(pointX,poingY)是否在方形区域内
     */
    private boolean inRect(float pointX, float pointY) {
        if (pointX >= rectXLeft && pointX <= rectXRight && pointY >= rectYLeft && pointY <= rectYRight) {
            return true;
        }
        return false;
    }

    /**
     * 检测是否点击在调色环上
     *
     * @param pointX 触控点的X坐标
     * @param pointY 触控点的Y坐标
     * @return boolean, 代表是否在调色环内
     */
    private boolean inCircle(float pointX, float pointY) {

        float innerRadius = radius - (paint.getStrokeWidth() + pickerCirclePaint.getStrokeWidth()) / 2;
        float outterRadius = radius + (paint.getStrokeWidth() - pickerCirclePaint.getStrokeWidth()) / 2;
        float width = getWidth() / 2;
        float height = getHeight() / 2;

        //Log.d(getClass().getName(),"input:x ="+pointX+"y= "+pointY+"radius="+radius);
        //Log.d(getClass().getName(),"input:width ="+width+" height="+height);

        float pointRadius = (pointX - width) * (pointX - width) + (pointY - height) * (pointY - height);
        if (innerRadius * innerRadius < pointRadius && pointRadius < outterRadius * outterRadius) {
            return true;
        }
        return false;
    }
}
