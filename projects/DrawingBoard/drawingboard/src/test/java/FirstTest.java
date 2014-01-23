import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XMLParser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class FirstTest {
    @Test
    public void testInstantiation() {
        String test = "<a><b name = \"exit\"/><c></c></a>";
        InputStream is = new ByteArrayInputStream(test.getBytes());
        Block root = XMLParser.getRootBlock(is);
        ArrayList<Block> list = (ArrayList<Block>) root.getSubBlocks();
        Block rootBlock = list.get(0);
        Block block = null;
        String result = rootBlock.getName();
        Iterator<Block> blockIterator = rootBlock.blockIterator();
        while (blockIterator.hasNext()) {
            block = (Block) blockIterator.next();
            System.out.println(block.getName());
        }
        assertThat(block.getName(), equalTo("c"));
    }
}