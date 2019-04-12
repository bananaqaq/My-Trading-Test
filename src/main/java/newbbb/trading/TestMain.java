package newbbb.trading;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");
        TradingTest instance = new TradingTest();
        instance.makeTrade();


    }

}
