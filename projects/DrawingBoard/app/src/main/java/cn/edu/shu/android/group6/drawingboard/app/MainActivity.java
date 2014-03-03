package cn.edu.shu.android.group6.drawingboard.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.shu.android.group6.drawingboard.app.core.tool.ToolManager;
import cn.edu.shu.android.group6.drawingboard.app.core.view.PaintCanvas;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Toolbox;

public class MainActivity extends Activity {
    private static final App app = App.getInstance();
    private PaintCanvas paintCanvas;
    private RelativeLayout container;

    //条色盘测试
    //private ColorPanel colorPanel = null;
    //private ImageView colorPanelBar = null;
    //private ImageView transparentLayer = null;
    //private ColorPickerFragment colorPickerFragment = null;

    /**
     * 创建流程：
     * 1、在App里注册MainActivity
     * 2、加载画布
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //register main activity
        app.setMainActivity(this);

        //get the container
        setContentView(R.layout.activity_main);
        container = (RelativeLayout) findViewById(R.id.main_container);

        //new PaintCanvas & register it
        paintCanvas = new PaintCanvas(this);
        container.addView(paintCanvas);
        app.setPaintCanvas(paintCanvas);

        //test paint


        //add the TestFragment
        getFragmentManager().beginTransaction().add(R.id.main_container, new TestFragment()).commit();

        //add Toolbox
        try {
            ToolManager.load("Assets://tools/Circle.xml");
            ToolManager.load("Assets://tools/Curve.xml");
            ToolManager.load("Assets://tools/Line.xml");
            ToolManager.load("Assets://tools/Rectangle.xml");
            ToolManager.load("Assets://tools/RoundRectangle.xml");
            app.setCurrentTool(ToolManager.load("Assets://tools/Select.xml"));
            ToolManager.load("Assets://tools/PlayAnimation.xml");
            ToolManager.load("Assets://tools/Delete.xml");
            ToolManager.load("Assets://tools/TranslateAnimation.xml");
            ToolManager.load("Assets://tools/CocaCola/CocaCola/");
            ToolManager.load("Assets://tools/CocaCola/CokeZero/");
            ToolManager.load("Assets://tools/CocaCola/DietCoke/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getFragmentManager().beginTransaction().add(R.id.main_container, new Toolbox()).commit();

        //test control point
//        ControlPoint controlPoint = new ControlPoint(this);
//        controlPoint.setX(100);
//        controlPoint.setY(100);
//        paintCanvas.getMirror().addView(controlPoint);
//        paintCanvas.getMirror().requestLayout();

//        setContentView(R.layout.activity_main);
//        CanvasElement element = new CanvasElement(this);
//        Oval oval = new Oval();
//        Path path = new Path();
//        path.moveTo(20,20);
//        path.lineTo(20,180);
//        path.lineTo(60,100);
//        path.quadTo(160,-100,150,180);
//        path.lineTo(100,100);
//        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path,200,200));
        //ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{0,0,0,0,0,0,0,0},new RectF(40,40,160,160),new float[]{50,50,50,50,50,50,50,50}));
        //ShapeDrawable drawable = new ShapeDrawable(new ArcShape(0, 270));
//        GaiaCircle drawable = new GaiaCircle();
//        drawable.setRadius(50);
//        drawable.getPaint().setStyle(Paint.Style.STROKE);
//        drawable.getPaint().setStrokeWidth(10);
//        drawable.getPaint().setAntiAlias(true);
//        drawable.getPaint().setColor(Color.RED);
        //drawable.setPadding(6,6,6,6);
        //drawable.setBounds(0, 0, 200, 200);
//        oval.setDrawable(drawable);
//        element.setDrawable(drawable);
//        RelativeLayout container = (RelativeLayout) findViewById(R.id.main_container);
//        element.setX(50);
//        element.setY(50);
//        container.addView(element);
//调色盘测试
//        p_color = (TextView) findViewById(R.id.info_paint_color);
//        p_size = (TextView) findViewById(R.id.info_paint_size);
//        p_style = (TextView) findViewById(R.id.info_paint_style);
//        pc_num = (TextView) findViewById(R.id.info_currentpc_element_num);
//
//        getFragmentManager().beginTransaction().add(R.id.main_container, new FloatPanel()).commit();
//
//        transparentLayer = (ImageView) findViewById(R.id.transparency_layer);
//        colorPanelBar = (ImageView) findViewById(R.id.color_panle_bar);
//        colorPanel = (ColorPanel) findViewById(R.id.color_panle);
//
//        colorPanel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                colorPanel.setColor(app.getCurrentPaint().getColor());
//                colorPanel.invalidate();
//            }
//        });
//        colorPanel.setColor(app.getCurrentPaint().getColor());
//
//        colorPickerFragment = new ColorPickerFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.main_container,colorPickerFragment);
//        fragmentTransaction.commit();
//
//        hideTransparentLayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //功能菜单
        switch (item.getItemId()) {
            //工具箱
            case R.id.menu_toolbox:
                //new ToolboxFragment().show(getFragmentManager(), "toolbox");
                break;
            //清空画布，后面要改为删除工具
            case R.id.menu_clean_caves:
                //app.getCurrentPaintCanvas().clear();
                break;
            //添加工具，后面在工具箱内实现
            case R.id.menu_add_tool:
                break;
            //删除工具，后面在工具箱内实现
            case R.id.menu_remove_tool:
                //new RemoveToolFragment().show(getFragmentManager(), "remove_tool");
                break;
            //保存画布，调用PaintCanvas的save()方法,存为一个xml文件在gallery文件夹中
            case R.id.menu_save_canvas:
                break;
            //保存模板，调用PaintCanvas的saveTemplate()方法，存为xml文件在template中
            case R.id.menu_save_template:
                break;
            //调画笔的颜色，后面转到画笔调节统一设置
            case R.id.menu_paint_color:
                /*new PaintColorPickerDialog(this, this, app.getCurrentPaint().getColor())
                        .show();*/
                return true;
            //调节画笔大小，后面转到画笔调节统一设置
            case R.id.menu_paint_size:
                /*new PaintSizePickerDialog(this, (int) app.getCurrentPaint().getStrokeWidth())
                        .show(getFragmentManager(), "paint_size");*/
                return true;
            //调节画笔类型，后面改为填充工具
            case R.id.menu_paint_style:
                /*new PaintStylePickerDialog(this, app.getCurrentPaint().getStyle())
                        .show(getFragmentManager(), "paint_style");*/
                return true;
            //统一的程序设置
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void colorChanged(int color) {
        //app.getCurrentPaint().setColor(color);
        //colorPanel.setColor(color);
        //colorPanel.invalidate();
    }

    @Override
    public void sizeChanged(int size) {
        //app.getCurrentPaint().setStrokeWidth(size);
    }

    @Override
    public void styleChanged(Paint.Style style) {
        //app.getCurrentPaint().setStyle(style);
    }*/

    public void hideColorPanle() {
        /*if (colorPanel != null) {
            colorPanel.setVisibility(View.INVISIBLE);
        }
        colorPanelBar.setVisibility(View.INVISIBLE);*/
    }

    public void showColorPanle() {
        /*if (colorPanel != null) {
            colorPanel.setVisibility(View.VISIBLE);
        }
        colorPanelBar.setVisibility(View.VISIBLE);*/
    }

    public void hideTransparentLayer() {
        /*if (transparentLayer != null) {
            transparentLayer.setVisibility(View.INVISIBLE);
        }*/
    }

    public void showTransparentLayer() {
        /*if (transparentLayer != null) {
            transparentLayer.setVisibility(View.VISIBLE);
        }*/
    }

    public void testUpdatePaintCanvas() {
        TextView text = new TextView(this);
        text.setText("I'm a test message");
        paintCanvas.getArtwork().addView(text);
    }

    private class TestFragment extends Fragment {
        //        public static final int UPDATE_CANVAS_ELEMENT_COUNT = 1;
        private TextView tvCanvasElementCount;
//        private Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                switch (msg.what) {
//                    case UPDATE_CANVAS_ELEMENT_COUNT:
//                        tvCanvasElementCount.setText(paintCanvas.getArtwork().getChildCount());
//                        break;
//                }
//                return true;
//            }
//        });

        private void updateInfo() {
            tvCanvasElementCount.setText(Integer.toString(paintCanvas.getArtwork().getChildCount()));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_test, container, false);
            //root.setBackgroundColor(Color.LTGRAY);
            Button test = (Button) root.findViewById(R.id.test);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //testUpdatePaintCanvas();
//                    SelectTool tool = new SelectTool();
//                    tool.startUsingOn(paintCanvas);
                    //mirror.addView(copy);
                    TextView text = new TextView(v.getContext());
                    text.setText("message");
                    paintCanvas.getMirror().addView(text);
                }
            });
            tvCanvasElementCount = (TextView) root.findViewById(R.id.test_canvas_element_count);
            Button update = (Button) root.findViewById(R.id.test_update_info);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateInfo();
                }
            });
            return root;
        }
    }
}
