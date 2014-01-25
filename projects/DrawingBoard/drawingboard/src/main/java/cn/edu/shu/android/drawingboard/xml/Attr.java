package cn.edu.shu.android.drawingboard.xml;

/**
 * Created by yy on 1/23/14.
 * Attr类是对属性的描述，属性一般是属性名称和对应的值。
 */
public class Attr {
    private String name;
    private String value;

    public Attr(String name,String value){
        setName(name);
        setValue(value);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
