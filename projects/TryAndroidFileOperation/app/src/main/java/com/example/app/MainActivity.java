package com.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.util.Iterator;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_raw = (Button)findViewById(R.id.btn_raw);
        btn_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream is = getResources().openRawResource(R.raw.a);
                //bug 1 root
                Block root = XMLParser.getRootBlock(is).getFirstSubBlock();
                Log.i("yy",root.getName());
                Log.i("yy",root.getFirstSubBlock().getName());
                Log.i("yy",String.format("Sub block count: %d", root.subBlockCount())) ;
                Block structure = root.getFirstSubBlock();
                for(Iterator<Block> i = structure.blockIterator();i.hasNext();)
                {
                    Block b = i.next();
                    Log.i("yy",String.format("Sub block: %s", b.getName())) ;
                }
                Block point = structure.getFirstSubBlock();
                for(Iterator<Attr> i = point.attrIterator();i.hasNext();)
                {
                    Attr a = i.next();
                    Log.i("yy",String.format("point block: %s", a.getName())) ;
                    Log.i("yy",String.format("point block: %s", a.getValue())) ;
                }
                //bug 2 num != 3
                Log.i("yy",String.format("point block num: %d", structure.getSubBlocksByName("point").size()));
                //bug 3 cannot get it
                Log.i("yy",String.format("point size: %s", point.getAttrValue("size"))) ;
                //p.s. add XMLParserBaseException
            }
        });

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

}
