package cn.edu.shu.drawingboard.core;

import java.util.ArrayList;
import java.util.List;

public class PaintCanvas {
	private class CanvasElement{
		public Element e;
		public Position pos;
		public CanvasElement(Element e,Position p){
			this.e = e;
			this.pos = p;
		}
	};
	private List<CanvasElement> elements = new ArrayList<CanvasElement>();
	public void addElement(Element e,Position pos){
		elements.add(new CanvasElement(e,pos));
	}
	public Position getPosition(Element e){
		for(CanvasElement ce : elements){
			if(ce.e.equals(e)){
				return ce.pos;
			}
		}
		return null;
	}
}
