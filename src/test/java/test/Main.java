package test;

import com.jstatic.StaticManager;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * @author 谢俊权
 * @create 2016/2/16 14:52
 */
public class Main {

    public static void main(String[] args) throws DocumentException, IOException {

        StaticManager sm = new StaticManager("static.xml");
        sm.run();
    }
}
