package cn.edu.shu.android.drawingboard.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by otakuplus on 14-2-22.
 */
public class ColorCircleView extends View {

    private Paint paint = null;
    private Paint centerPaint = null;

    private int[] circleColors = null;
    private int[] rectColors = null;

    private float radius;
    private float rectXLeft, rectYLeft, rectXRight, rectYRight;

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
        // 3d图像处理概念当中的渲染器，产生扫描线。前两个是参数是中心坐标，后眠是颜色列表（起始颜色到结束颜色），最后一个参数是颜色坐标
        // 一般设置为null。
        Shader shader = new SweepGradient(0, 0, circleColors, null);
        // 设置抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);

        // 初始化正方形选择区颜色
        rectColors = circleColors.clone();
        shader = new SweepGradient(0, 0, rectColors, null);
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // 计算圆环半径
        radius = (width - paint.getStrokeWidth()) * 0.35f;

        // 画笔设置为填充
        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setStrokeWidth(36);
        rectXLeft = width * 03f;
        rectYLeft = height * 0.3f;
        rectXRight = width * 0.7f;
        rectYRight = height * 0.7f;
        canvas.drawRect(rectXLeft, rectYLeft, rectXRight, rectYRight, centerPaint);

        // 画笔设置为实心
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(36);
        // 绘制彩色圆环
        canvas.translate(width / 2, height / 2);
        canvas.drawOval(new RectF(-radius, -radius, radius, radius), paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        return super.onTouchEvent(event);
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
}
