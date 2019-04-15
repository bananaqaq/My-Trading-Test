package newbbb.trading;

import newbbb.constant.NBGlobalConfig;
import newbbb.model.Account;
import newbbb.model.Coin;
import newbbb.service.IAccountService;
import newbbb.service.ICoinService;
import newbbb.service.IRedisService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

public class TestMain {

    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");

        IRedisService redisService = context.getBean(IRedisService.class);
        ICoinService coinService = context.getBean(ICoinService.class);
        IAccountService accountService = context.getBean(IAccountService.class);


        // 加载所有币种
        List<Coin> coinList = coinService.getList();
        NBGlobalConfig.COINS = new Coin[coinList.get(coinList.size() - 1).getId() + 1];
        for(Coin coin : coinList){
            NBGlobalConfig.COINS[coin.getId()] = coin;
        }






        // 添加Coin数据
        /*long timeNow = new Date().getTime();
        Coin c1 = new Coin();
        c1.setEnName("BTC");
        c1.setZhName("比特币");
        c1.setCreateTime(timeNow);
        c1.setUpdateTime(timeNow);

        Coin c2 = new Coin();
        c2.setEnName("USDT");
        c2.setZhName("泰达币");
        c2.setCreateTime(timeNow);
        c2.setUpdateTime(timeNow);

        coinService.add(c1);
        coinService.add(c2);*/


    }

    public static void main(String[] args){
        TradingTest instance = context.getBean(TradingTest.class);
//        instance.makeTrade(null);


    }


}
