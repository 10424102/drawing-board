package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;

public class MainActivity extends Activity implements PaintColorPickerDialog.OnColorChangedListener,
        PaintSizePickerDialog.OnSizeChangedListener,
        PaintStylePickerDialog.OnStyleChangedListener {
    public static final MyApplication app = MyApplication.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(new PaintCanvas(this));

        PaintCanvas pc = (PaintCanvas) findViewById(R.id.my_canvas);
        app.setPaintCanvas(pc);


        app.setMainActivity(this);


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
                new PaintColorPickerDialog(this, this, app.paint.getColor())
                        .show();
                return true;
            case R.id.menu_paint_size:
                new PaintSizePickerDialog(this, (int) app.paint.getStrokeWidth())
                        .show(getFragmentManager(), "paint_size");
                return true;
            case R.id.menu_paint_style:
                new PaintStylePickerDialog(this, app.paint.getStyle())
                        .show(getFragmentManager(), "paint_style");
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorChanged(int color) {
        app.paint.setColor(color);
    }

    @Override
    public void sizeChanged(int size) {
        app.paint.setStrokeWidth(size);
    }

    @Override
    public void styleChanged(Paint.Style style) {
        app.paint.setStyle(style);
    }
}
