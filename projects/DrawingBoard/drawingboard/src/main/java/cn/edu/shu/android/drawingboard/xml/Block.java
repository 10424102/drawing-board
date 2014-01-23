package cn.edu.shu.android.drawingboard.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yy on 1/23/14.
 * Block指XML文档当中的一个标签内的内容
 */
public class Block {
    private String name = null;
    private ArrayList<Block> blockArrayList = null;
    private ArrayList<Attr> attrArrayList = null;

    public Block() {
        blockArrayList = new ArrayList<Block>();
        attrArrayList = new ArrayList<Attr>();
    }


    public Iterator<Block> blockIterator() {
        return null;
    }

    public Iterator<Attr> attrIterator() {
        return null;
    }

    /**
     * 返回该Block的名称
     *
     * @return Block的名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置该Block的名称
     *
     * @param name Block的新名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回子Block的数目
     *
     * @return 子Block的数目
     */
    public int subBlockCount() {
        if (blockArrayList != null) {
            return blockArrayList.size();
        } else {
            return 0;
        }
    }

    /**
     * 返回子Block列表中的第一个Block
     *
     * @return 子Block列表中的第一个Block
     */
    public Block getFirstSubBlock() {
        Block firstSubBlock = null;
        if (blockArrayList != null && blockArrayList.isEmpty() == false) {
            firstSubBlock = blockArrayList.get(0);
        }
        return firstSubBlock;
    }

    /**
     * 返回该Block的子Block List
     *
     * @return 该Block的子Block List
     */
    public List<Block> getSubBlocks() {
        return blockArrayList;
    }

    public String getAttrValue(String attrName) {
        return null;
    }

    /**
     * 检测该Block是否含有子Block
     *
     * @return 返回true表示含有非空子Block，返回false表示不含子Block
     */
    public boolean hasSubBlock() {
        if (blockArrayList != null) {
            return blockArrayList.isEmpty();
        } else {
            return false;
        }
    }

    /**
     * @param subBlockName 指定的Block名称
     * @return List<Block> 名称为subBlockName的Block构成的List
     * 返回名为subBlockName的Block List
     */
    public List<Block> getSubBlocksByName(String subBlockName) {
        ArrayList<Block> resultBlockList = new ArrayList<Block>();
        Block block = null;
        if (blockArrayList != null && blockArrayList.isEmpty() == false) {
            Iterator<Block> iterator = blockArrayList.iterator();
            while (iterator.hasNext()) {
                block = iterator.next();
                if (block != null && block.getName().equals(subBlockName)) {
                    resultBlockList.add(block);
                }
            }
        }
        return resultBlockList;
    }


    /**
     * 添加该Block的子block
     *
     * @param block 要添加的子Block
     */
    public void addSubBlock(Block block) {
        if (blockArrayList != null) {
            blockArrayList.add(block);
        }
    }

    /**
     * 添加该Block的属性
     *
     * @param attr 要添加的属性
     */
    public void addAttr(Attr attr) {
        if (attrArrayList != null) {
            attrArrayList.add(attr);
        }
    }
}
