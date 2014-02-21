import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.String;
import java.lang.System;
import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.shu.android.drawingboard.xml.XmlParser;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class FirstTest {
    @Test
    public void testInstantiation() {
        String test = "<a><b name = \"exit\"/><c></c></a>";
        String value = null;
        InputStream is = new ByteArrayInputStream(test.getBytes());
        try {
            Block root = XmlParser.getRootBlock(is);
            ArrayList<Block> list = (ArrayList<Block>) root.getSubBlocks();
            Block block = null;
            String result = root.getName();
            Iterator<Block> blockIterator = root.blockIterator();
            while (blockIterator.hasNext()) {
                block = (Block) blockIterator.next();
                if (block.getName().equals("b")) {
                    String value = root.getAttrValue("name");
                }
                System.out.println(block.getName());
            }
            assertThat(block.getName(), equalTo("c"));
            assertThat(value, equalTo("exit"));
        } catch (XmlParserException e) {
            Log.e("getRootBlocks error");
            System.out.println(e.printStackTrace());
        }
    }
}