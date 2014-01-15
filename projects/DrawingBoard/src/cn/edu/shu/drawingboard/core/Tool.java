package cn.edu.shu.drawingboard.core;

import java.net.URL;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class Tool {
	private ElementGroup structure = new ElementGroup();
	private Animation animation = new Animation();
	private UserInterface ui = new UserInterface();
	public void setIcon(URL url){
		ui.setIconUrl(url);
	}
	public View getView(Context context){
		Button btn = new Button(context);
		btn.setText("Load tool's icno Here");
		return btn;
	}
}
