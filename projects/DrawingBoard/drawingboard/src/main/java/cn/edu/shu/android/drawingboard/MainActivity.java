package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;
import cn.edu.shu.android.drawingboard.core.tool.Tool;
import cn.edu.shu.android.drawingboard.core.tool.ToolManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(new PaintCanvas(this));

        PaintCanvas pc = (PaintCanvas)findViewById(R.id.my_canvas);
        MyApplication app = MyApplication.getInstance();
        app.setPc(pc);

        ToolManager toolManager = ToolManager.getInstance();
        Tool t = toolManager.getToolById(1);
        View v = t.getView(this);

        addContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        try {
//            InputStream is = openFileInput(Environment.getExternalStorageDirectory().toString());
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }

        //app.getCanvas().drawColor(Color.BLACK);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_toolbox:
                ToolboxFragment f = new ToolboxFragment();
                f.show(getFragmentManager(), "toolbox");
                break;
            case R.id.menu_add_tool:
                break;
            case R.id.menu_remove_tool:
                break;
            case R.id.menu_save_canvas:
                break;
            case R.id.menu_save_template:
                break;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
