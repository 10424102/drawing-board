package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cn.edu.shu.android.drawingboard.view.PaintCanvas;

public class MainActivity extends Activity implements PaintColorPickerDialog.OnColorChangedListener,
        PaintSizePickerDialog.OnSizeChangedListener,
        PaintStylePickerDialog.OnStyleChangedListener {
    public static final MyApplication app = MyApplication.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PaintCanvas pc = (PaintCanvas) findViewById(R.id.my_canvas);
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
//
//        Button btnTest1 = (Button)findViewById(R.id.btn_test_1);
//        btnTest1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                app.error("11111111111111");
//                app.error("22222222222222");
//                app.error("33333333333333");
//                app.showError();
//            }
//        });
//        Button btnTest2 = (Button)findViewById(R.id.btn_test_2);
//        btnTest2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                app.error("11111111111111");
//                app.error("22222222222222");
//                app.error("33333333333333");
//                app.hideError();
//            }
//        });

        //pc.bringToFront();
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
                app.getPaintCanvas().clear();
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
                new PaintColorPickerDialog(this, this, app.getPaint().getColor())
                        .show();
                return true;
            case R.id.menu_paint_size:
                new PaintSizePickerDialog(this, (int) app.getPaint().getStrokeWidth())
                        .show(getFragmentManager(), "paint_size");
                return true;
            case R.id.menu_paint_style:
                new PaintStylePickerDialog(this, app.getPaint().getStyle())
                        .show(getFragmentManager(), "paint_style");
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorChanged(int color) {
        app.getPaint().setColor(color);
    }

    @Override
    public void sizeChanged(int size) {
        app.getPaint().setStrokeWidth(size);
    }

    @Override
    public void styleChanged(Paint.Style style) {
        app.getPaint().setStyle(style);
    }
}
