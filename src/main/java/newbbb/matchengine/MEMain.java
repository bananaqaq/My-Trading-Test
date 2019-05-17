package newbbb.matchengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MEMain {

    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");
    }

    public static  void main(String[] args){

        Logger log = LoggerFactory.getLogger(MEMain.class);

    }

}
