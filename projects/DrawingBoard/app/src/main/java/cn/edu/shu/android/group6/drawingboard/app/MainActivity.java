package cn.edu.shu.android.group6.drawingboard.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {
    private static final App app = App.getInstance();

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
        app.setMainActivity(this);
        setContentView(R.layout.activity_main);
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
}
