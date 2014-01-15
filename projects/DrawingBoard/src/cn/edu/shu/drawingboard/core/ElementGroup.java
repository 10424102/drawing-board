package cn.edu.shu.drawingboard.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
/**
 * @see ElementGroup也是Element只是其中包含了一个基本的Element（点、线、面）
 * @author yy
 *
 */
public class ElementGroup extends Element{
	private List<Element> elements = new ArrayList<Element>();//按照zindex排序，从小到大。
	public void draw(Canvas canvas){
		for(Element e:elements){
			e.draw(canvas);
		}
	}
	/**
	 * 
	 * @param e
	 */
	public void addElement(Element e){
		//TODO 检测zindex是否有重复
		elements.add(e);
	}
}
