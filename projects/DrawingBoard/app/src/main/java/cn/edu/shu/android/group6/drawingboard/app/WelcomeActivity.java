package cn.edu.shu.android.group6.drawingboard.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.core.Config;
import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;
import cn.edu.shu.android.group6.drawingboard.app.core.tool.ToolManager;

/**
 * Created by yy on 3/4/14.
 */
public class WelcomeActivity extends Activity {
    private static final App app = App.getInstance();

    TextView loadToolText;
    Handler handler;

/**
 * Created by Fanlu on 14-3-4.
 */
public class WelcomeActivity extends Activity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);
        ImageView mImageView = (ImageView) findViewById(R.id.logo);
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(3000);
        mImageView.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {

                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationStart(Animation arg0) {

            }

        });
        
        getActionBar().hide();
        setContentView(R.layout.activity_welcome);
        loadToolText = (TextView) findViewById(R.id.welcome_load_tool_text);

        // Font path
        String fontPath = "welcome_title_font.ttf";

        // text view label
        TextView txtGhost = (TextView) findViewById(R.id.welcome_title);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtGhost.setTypeface(tf);

        new LoadToolTask().execute(Config.toolList());

    }

    private class LoadToolTask extends AsyncTask<List<URI>, Void, Void> {
        private String loadMsg;

        protected Void doInBackground(List<URI>... urlLists) {
            int i = 0;
            int loadSuccess = 0;
            for (URI uri : urlLists[0]) {
                loadMsg = "Now loading " + uri;
                Tool t = ToolManager.load(uri);
                if (t != null) loadSuccess++;
                publishProgress();
                // Escape early if cancel() is called
                if (isCancelled()) break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            loadMsg = "Successful loaded  " + loadSuccess + " tools.";
            publishProgress();
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            loadToolText.setText(loadMsg);
        }

        protected void onPostExecute(Void result) {
        }
    }
}
