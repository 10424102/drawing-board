package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;

import cn.edu.shu.android.drawingboard.core.Generable;
import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;
import cn.edu.shu.android.drawingboard.core.Position;
import cn.edu.shu.android.drawingboard.core.transform.Transform;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * 这是“元素”基类，所有画面中的东西都是元素。
 * 元素可以变形（Transform）
 * 元素可以有动画（Animation）
 * 工具（Tool）可以生成元素，不同的元素生成方法不同，步骤也不尽相同。
 * 元素有层次之分，zindex
 * 每个元素都有一个唯一的id
 *
 * @author 杨阳
 *
 * @version 1.0
 *
 */
public abstract class Element  implements Generable {
    /**
     * Element 类的计数器
     */
    private static int id_count = 0;

    /**
     * 每个Element唯一的ID
     */
    protected int id;

    /**
     * 每个Element的层次，小的在下面
     */
    protected int zindex;

    /**
     * 每个Element的在画布的中心位置
     */
    protected Position center;

    /**
     * 每个Element所在的画布
     */
    protected Canvas canvas;

    private String XMLBlockName = "element";

    /**
     * 无参数构造器
     */
    public Element() {
        id_count = id_count + 1;
        id = id_count;
    }

    public String getXMLBlockName() {

        return XMLBlockName;
    }
    //TODO add path

    public void setXMLBlockName(String XMLBlockName) {
        this.XMLBlockName = XMLBlockName;
    }

    /**
     * 设置画布
     * @param canvas 画布
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * 获得zindex
     * @return zindex
     */
    public int getZindex() {

        return zindex;
    }

    /**
     * 设置zindex
     * @param zindex
     */
    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    /**
     * 获得Element的中心
     * @return 中心
     */
    public Position getCenter() {

        return center;
    }

    /**
     * 设置中心
     * @param center
     */
    public void setCenter(Position center) {
        this.center = center;
    }

    /**
     * 获得元素的唯一ID
     * @return 唯一ID
     */
    public int getId() {
        return id;
    }

    /**
     * 抽象方法，绘制
     */
    public abstract void draw();

    /**
     * 抽象方法变换，有了绘制和变换,再加上路径就能实现动画
     * @param t 变换的基类Transform
     */
    public abstract void transform(Transform t);

    /**
     * 抽象方法从XML初始化对象
     * @param root XML数据块
     * @throws cn.edu.shu.android.drawingboard.core.exception.ParserXMLException
     */
    public abstract void loadXML(Block root) throws ParserXMLException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;

        Element element = (Element) o;

        if (id != element.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                '}';
    }

}
