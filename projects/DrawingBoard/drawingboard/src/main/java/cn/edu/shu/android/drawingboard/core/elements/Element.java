package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;

import cn.edu.shu.android.drawingboard.core.Generable;
import cn.edu.shu.android.drawingboard.core.exception.BuildElementException;
import cn.edu.shu.android.drawingboard.core.transform.Transform;
import cn.edu.shu.android.drawingboard.xml.Block;

public abstract class Element implements Generable {
    private static int id_count = 0;
    protected int id;
    protected int zindex;
    protected Position center;
    protected Canvas canvas;

    public Element(Block b) throws BuildElementException
    {
        setId(++id_count);
    }

    private void setId(int id) {
        this.id = id;
    }

    public Element() {
        setId(++id_count);
    }

    //TODO add path

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public int getZindex() {

        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    public Position getCenter() {

        return center;
    }

    public void setCenter(Position center) {
        this.center = center;
    }
    public void setCenter(Block b) throws BuildElementException{
        center = new Position(b);
    }

    public int getId() {
        return id;
    }

    public abstract void draw();

    public abstract void transform(Transform t);

    public abstract void loadXML(Block root) throws BuildElementException;

}
