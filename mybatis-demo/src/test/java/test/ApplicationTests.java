package test;

import com.dyb.springcloud.DemoApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class ApplicationTests {

    @Before
    public void init(){
        System.out.println("******测试开始");
    }

    @After
    public void end(){
        System.out.println("******测试结束");
    }

    @BeforeClass
    public static void initClass(){
        System.out.println("******测试开始初始化");
    }

    @AfterClass
    public static void endClass(){
        System.out.println("******测试结束初始化");
    }
}
