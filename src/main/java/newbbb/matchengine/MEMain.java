package newbbb.matchengine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class MEMain {

    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");
    }

    public static  void main(String[] args){

        ConcurrentSkipListSet<String> list = new ConcurrentSkipListSet<>();
        Iterator<String> iterator = list.iterator();
        System.out.println(iterator.hasNext());

    }

}
