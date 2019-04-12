package newbbb.trading;

import newbbb.service.IAccountService;
import newbbb.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TradingTest {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IAccountService accountService;


    public void makeTrade(){

        System.out.println(redisService == null);

    }

}
