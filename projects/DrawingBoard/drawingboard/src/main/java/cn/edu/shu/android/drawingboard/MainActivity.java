package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.IOException;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;
import cn.edu.shu.android.drawingboard.core.PhraseXMLException;
import cn.edu.shu.android.drawingboard.core.Tool;
import cn.edu.shu.android.drawingboard.core.ToolManager;
import cn.shu.edu.android.drawingboard.R;

public class MainActivity extends Activity {

    private ToolManager mToolManager = ToolManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
        }
        SAXReader reader = new SAXReader();
        Document document;
        Tool t = null;
        FileInputStream inputStream;
        try {
            inputStream = this.openFileInput("a.xml");
        } catch (IOException e) {
            Log.i("yy", "Cannot open a.xml");
            return;
        }
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            Log.i("yy", e.toString());
            return;
        }
        try {
            t = mToolManager.buildToolByXML(document);
        } catch (PhraseXMLException e) {
            Log.i("yy", e.toString());
            Log.i("yy", "初始化工具失败");
        }
        Button btn = (Button)t.getView(this);
        this.addContentView(btn, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        PaintCanvas pc = new PaintCanvas(this);
        this.addContentView(pc, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        MyApplication app = MyApplication.getInstance();
        app.pc = pc;
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
