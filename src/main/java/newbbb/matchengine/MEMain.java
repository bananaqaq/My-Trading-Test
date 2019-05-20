package newbbb.matchengine;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class MEMain {

    private static ApplicationContext context;

    static {
//        context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");
    }

    public static void main(String[] args){

        JSONObject person = new JSONObject();
        person.put("name", "zhangsan");
        person.put("age", 18);
        changeJSON(person);
        System.out.println(person.toJSONString());
        HashMap<String, String> map = new HashMap<>();
        map.size();

    }

    public static void changeJSON(JSONObject json){
        json.put("gender", "man");
    }

}
