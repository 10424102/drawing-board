package cn.edu.shu.android.group6.drawingboard.app.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;

/**
 * Created by yy on 3/4/14.
 */
public class Config {
    private static final App app = App.getInstance();

    static {
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
            @Override
            public URLStreamHandler createURLStreamHandler(String protocol) {
                switch (protocol) {
                    case "Assets":
                        return new URLStreamHandler() {

                            @Override
                            protected URLConnection openConnection(URL u) throws IOException {
                                return new URLConnection(u) {
                                    @Override
                                    public InputStream getInputStream() throws IOException {
                                        return app.getAssets().open(getURL().getPath());
                                    }

                                    @Override
                                    public void connect() throws IOException {

                                    }
                                };
                            }
                        };
                }
                return null;
            }
        });
    }

    public static List<URI> toolList() {
        List<URI> list = new ArrayList<>();
        try {
            InputStream is = app.getAssets().open("config.xml");
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element e = (Element) i.next();
                list.add(new URI(e.getText()));
            }
        } catch (IOException | DocumentException | URISyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }
}
