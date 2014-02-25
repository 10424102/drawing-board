package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.shu.android.drawingboard.ui.ColorCircleView;
import cn.edu.shu.android.drawingboard.ui.ColorPanel;
import cn.edu.shu.android.drawingboard.ui.ColorPickerFragment;
import cn.edu.shu.android.drawingboard.ui.FloatPanel;
import cn.edu.shu.android.drawingboard.view.PaintCanvas;

public class MainActivity extends Activity implements PaintColorPickerDialog.OnColorChangedListener,
        PaintSizePickerDialog.OnSizeChangedListener,
        PaintStylePickerDialog.OnStyleChangedListener {
    public static final MyApplication app = MyApplication.getInstance();
    private ColorPanel colorPanel = null;
    private ImageView colorPanelBar = null;
    private ImageView transparentLayer = null;

    TextView p_color;
    TextView p_size;
    TextView p_style;
    TextView pc_num;


    private ColorPickerFragment colorPickerFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PaintCanvas pc = (PaintCanvas) findViewById(R.id.my_canvas);
        app.setCurrentPaintCanvas(pc);
//        Errorbox errorbox = new Errorbox();
//        errorbox.setContent("Fuck You!");
//        getFragmentManager().beginTransaction().add(R.id.main_container, errorbox).commit();
//        getFragmentManager().beginTransaction()
//                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
//                .hide(errorbox)
//                .commit();
//        app.setPaintCanvas(pc);
        app.setMainActivity(this);
//        app.setErrorBox(errorbox);

        //final ImageView testBlur = (ImageView)findViewById(R.id.test_blur);
        Button btnTest1 = (Button) findViewById(R.id.btn_test_1);
        btnTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });
        Button btnTest2 = (Button) findViewById(R.id.btn_test_2);
        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //app.getCurrentPaintCanvas().bringCanvasElementToFront();
                //Bitmap bmp = BitmapUtil.takeScreenShot(app.getMainActivity());
                //Bitmap blur = BitmapUtil.fastblur(bmp,10);
                //testBlur.setImageBitmap(blur);
            }
        });

        //pc.bringToFront();

        p_color = (TextView) findViewById(R.id.info_paint_color);
        p_size = (TextView) findViewById(R.id.info_paint_size);
        p_style = (TextView) findViewById(R.id.info_paint_style);
        pc_num = (TextView) findViewById(R.id.info_currentpc_element_num);

        getFragmentManager().beginTransaction().add(R.id.main_container, new FloatPanel()).commit();

        colorPickerFragment = new ColorPickerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_container,colorPickerFragment);
        fragmentTransaction.commit();

        transparentLayer = (ImageView) findViewById(R.id.transparency_layer);
        colorPanelBar = (ImageView) findViewById(R.id.color_panle_bar);
        colorPanel = (ColorPanel) findViewById(R.id.color_panle);

        colorPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.this.getFragmentManager().beginTransaction().show(colorPickerFragment).commit();
                colorPanel.setColor(app.getCurrentPaint().getColor());
                colorPanel.invalidate();
            }
        });
        colorPanel.setColor(app.getCurrentPaint().getColor());

        hideTransparentLayer();
    }

    public void updateInfo() {
        int alpha = Color.alpha(app.getCurrentPaint().getColor());
        int red = Color.red(app.getCurrentPaint().getColor());
        int green = Color.green(app.getCurrentPaint().getColor());
        int blue = Color.blue(app.getCurrentPaint().getColor());
        p_color.setText(String.format("A:%d, R:%d, G:%d, B:%d", alpha, red, green, blue));
        p_size.setText(Float.toString(app.getCurrentPaint().getStrokeWidth()));
        p_style.setText(app.getCurrentPaint().getStyle().toString());
        pc_num.setText(Integer.toString(app.getCurrentPaintCanvas().getCanvasElementNum()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_toolbox:
                new ToolboxFragment().show(getFragmentManager(), "toolbox");
                break;
            case R.id.menu_clean_caves:
                app.getCurrentPaintCanvas().clear();
                break;
            case R.id.menu_add_tool:

                break;
            case R.id.menu_remove_tool:
                new RemoveToolFragment().show(getFragmentManager(), "remove_tool");
                break;
            case R.id.menu_save_canvas:
                break;
            case R.id.menu_save_template:
                break;
            case R.id.menu_paint_color:
                getFragmentManager().beginTransaction().show(colorPickerFragment).commit();
                return true;
            case R.id.menu_paint_size:
                new PaintSizePickerDialog(this, (int) app.getCurrentPaint().getStrokeWidth())
                        .show(getFragmentManager(), "paint_size");
                return true;
            case R.id.menu_paint_style:
                new PaintStylePickerDialog(this, app.getCurrentPaint().getStyle())
                        .show(getFragmentManager(), "paint_style");
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorChanged(int color) {
        app.getCurrentPaint().setColor(color);
        colorPanel.setColor(color);
        colorPanel.invalidate();
    }

    @Override
    public void sizeChanged(int size) {
        app.getCurrentPaint().setStrokeWidth(size);
    }

    @Override
    public void styleChanged(Paint.Style style) {
        app.getCurrentPaint().setStyle(style);
    }

    public void hideColorPanle() {
        if (colorPanel != null) {
            colorPanel.setVisibility(View.INVISIBLE);
        }
        colorPanelBar.setVisibility(View.INVISIBLE);
    }

    public void showColorPanle() {
        if (colorPanel != null) {
            colorPanel.setVisibility(View.VISIBLE);
        }
        colorPanelBar.setVisibility(View.VISIBLE);
    }

    public void hideTransparentLayer() {
        if (transparentLayer != null) {
            transparentLayer.setVisibility(View.INVISIBLE);
        }
    }

    public void showTransparentLayer() {
        if (transparentLayer != null) {
            transparentLayer.setVisibility(View.VISIBLE);
        }
    }
}
