package cn.edu.shu.drawingboard.core;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Point extends Element{
	private Paint pen;
	private float x;
	private float y;
	private PaintCanvas pc;
	public Point(PaintCanvas pc){
		this.pc = pc;
	}
	@Override
	public void draw(Canvas c){
		c.drawPoint(x,y, pen);
	}

}
