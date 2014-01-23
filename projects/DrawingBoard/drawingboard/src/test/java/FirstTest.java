import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.clickOn;
import static org.robolectric.Robolectric.shadowOf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.String;
import java.util.ArrayList;

import cn.edu.shu.android.drawingboard.xml.XMLParser;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.Attr;

@RunWith(RobolectricTestRunner.class)
public class FirstTest {
    @Test
    public void testInstantiation() {
        String test = "<a><b name = \"exit\"/><c></c></a>";
        InputStream is = new ByteArrayInputStream(test.getBytes());
        Block root = XMLParser.getRootBlock(is);
        ArrayList<Block> list =(ArrayList<Block>)root.getSubBlocks();
        String result = list.get(0).getName();
        String rootname = root.getName();
        
        assertThat(result,equalTo("a"));
        assertThat(rootname,equalTo("Root0"));
    }
}