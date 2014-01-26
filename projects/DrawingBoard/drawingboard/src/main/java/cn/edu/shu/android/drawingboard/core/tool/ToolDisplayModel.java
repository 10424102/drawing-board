package cn.edu.shu.android.drawingboard.core.tool;

/**
 * Created by yy on 1/26/14.
 */
public class ToolDisplayModel {
    private int id;
    private String name;
    private String iconPath;
    public ToolDisplayModel(Tool t){
        id = t.getId();
        name = t.getName();
        iconPath = t.getIconPath();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }
}
