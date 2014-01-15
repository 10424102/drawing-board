package cn.edu.shu.drawingboard.core;

import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import android.util.Log;

public class XMLAnalyst {

	private void a_DrawingboardObject(org.dom4j.Element root,Tool t) throws CreateToolException{
		if(!root.getName().equals("drawingboard-object"))throw new CreateToolException("根元素不是drawingboard-object");
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
			org.dom4j.Element e = (org.dom4j.Element) i.next();
            switch(e.getName()){
            case "ui":
            	a_Ui(e,t);
            	break;
            case "structure":
            	a_Structure(e,t);
            	break;
            case "animation":
            	a_Animation(e,t);
            	break;
            }
        }
	}
	private void a_Structure(org.dom4j.Element root, Tool t) throws CreateToolException{
		
	}
	private void a_Animation(org.dom4j.Element root, Tool t) throws CreateToolException{
		
	}
	private void a_Ui(org.dom4j.Element root, Tool t) throws CreateToolException{
		
	}
	public Tool createTool(URL XMLFilePath) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(XMLFilePath);
			Tool tool = new Tool();
			a_DrawingboardObject(document.getRootElement(),tool);
	        
		} catch (DocumentException e) {
			Log.e("yy", "XMLAnalyst: createTool");
			e.printStackTrace();
		}catch(CreateToolException e){
			Log.e("yy",e.toString());
		}
		return null;
	}
}
