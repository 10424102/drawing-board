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
import android.os.Build;
import android.util.AttributeSet;
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
    boolean isRectTouch = false;

    private int circlePickColor;
    private int selectColor;
    private Shader alphaShader = null;

    private final static float PI = 3.1415927f;

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
        paint.setStrokeWidth(48);

        // 初始化正方形选择区颜色
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setStrokeWidth(36);
        // 画笔设置为填充
        centerPaint.setStyle(Paint.Style.FILL);

        pickerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pickerCirclePaint.setColor(Color.WHITE);
        pickerCirclePaint.setStyle(Paint.Style.STROKE);
        pickerCirclePaint.setStrokeWidth(8);

        circlePickColor = Color.WHITE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // 计算圆环半径
        radius = (width - paint.getStrokeWidth()) * 0.35f;

        rectXLeft = width * 0.325f;
        rectYLeft = height * 0.325f;
        rectXRight = width * 0.675f;
        rectYRight = height * 0.675f;

        if (rect == null) {
            rect = new RectF(-radius, -radius, radius, radius);
        }

        rectColors = null;
        rectColors = new int[]{
                Color.WHITE,
                Color.RED,
        };

        if (alphaShader == null) {
            alphaShader = new LinearGradient(rectXLeft, rectYLeft, rectXLeft, rectYRight, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
        }

        Shader shader2 = new LinearGradient(rectXLeft, rectYLeft, rectXRight, rectYLeft, Color.WHITE, circlePickColor, Shader.TileMode.CLAMP);
        ComposeShader shader3 = new ComposeShader(alphaShader, shader2, PorterDuff.Mode.MULTIPLY);
        centerPaint.setShader(shader3);
        canvas.translate(0, 0);
        canvas.drawRect(rectXLeft, rectYLeft, rectXRight, rectYRight, centerPaint);

        // 绘制彩色圆环
        canvas.translate(width / 2, height / 2);
        canvas.drawOval(rect, paint);

        if (isRectTouch) {
            canvas.translate(-width / 2, -height / 2);
            canvas.drawCircle(pickerX, pickerY, 20, pickerCirclePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        if ((isRectTouch = inRect(touchX, touchY)) || inCircle(touchX, touchY)) {
            pickerX = touchX;
            pickerY = touchY;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ;
                case MotionEvent.ACTION_MOVE:
                    if (isRectTouch) {
                        selectColor = interRectColor(touchX, touchY);
                    } else {
                        float angle = (float) java.lang.Math.atan2(touchY - getHeight() / 2, touchX - getWidth() / 2);
                        // need to turn angle [-PI ... PI] into unit [0....1]
                        float unit = angle / (2 * PI);
                        if (unit < 0) {
                            unit += 1;
                        }
                        circlePickColor = interCircleColor(circleColors, unit);
                        selectColor = circlePickColor;
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    invalidate();
                    break;
            }
        }
        return true;
    }

    public void setSelectColor(int color) {
        circlePickColor = color;
        selectColor = color;
    }

    public int getPickedColor() {
        return selectColor;
    }

    private int ave(int s, int d, float p) {
        return s + java.lang.Math.round(p * (d - s));
    }

    /**
     * 获取色调环上的颜色
     *
     * @param colors 扫描线数组
     * @param unit   色调环上的角度映射到数组中的数值
     * @return color 从色调盘上选择的颜色
     */
    private int interCircleColor(int colors[], float unit) {
        if (unit <= 0) {
            return colors[0];
        }
        if (unit >= 1) {
            return colors[colors.length - 1];
        }

        float p = unit * (colors.length - 1);
        int i = (int) p;
        p -= i;

        // now p is just the fractional part [0...1) and i is the index
        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), p);
        int r = ave(Color.red(c0), Color.red(c1), p);
        int g = ave(Color.green(c0), Color.green(c1), p);
        int b = ave(Color.blue(c0), Color.blue(c1), p);

        return Color.argb(a, r, g, b);
    }

    /**
     * 拾取方形区域的颜色
     *
     * @param pointX 接触点的X坐标
     * @param pointY 接触点的Y坐标
     * @return 拾取的颜色值
     */

    private int interRectColor(float pointX, float pointY) {
        int r, g, b;
        float colorFactor = (pointX - rectXLeft) / (rectXRight - rectXLeft);
        float brightnessFactor = (pointY - rectYLeft) / (rectYRight - rectYLeft);
        float[] hsv = new float[3];
        r = ave(Color.red(Color.WHITE), Color.red(circlePickColor), colorFactor);
        g = ave(Color.green(Color.WHITE), Color.green(circlePickColor), colorFactor);
        b = ave(Color.blue(Color.WHITE), Color.blue(circlePickColor), colorFactor);
        Color.RGBToHSV(r, g, b, hsv);
        // hsv空间当中亮度值从0%到100%，其中0%代表全黑
        hsv[2] =1.0f - brightnessFactor;
        return Color.HSVToColor(hsv);
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
