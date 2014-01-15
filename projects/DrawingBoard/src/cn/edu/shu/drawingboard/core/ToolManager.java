package cn.edu.shu.drawingboard.core;

import java.net.URL;

/**
 * Tool不用自己new，需要用ToolManager.load("xxx.xml");生成
 * 
 * @author yy
 *
 */
public class ToolManager {

	public Tool load(URL configureFilePath){
		XMLAnalyst a = new XMLAnalyst();
		Tool tool = a.createTool(configureFilePath);
		return tool;
	}
}
