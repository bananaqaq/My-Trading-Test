package newbbb.trading;

import newbbb.constant.NBGlobalConfig;
import newbbb.enums.TxDirectionEnum;
import newbbb.model.Account;
import newbbb.model.BuyOrder;
import newbbb.model.Coin;
import newbbb.model.TxPair;
import newbbb.service.IAccountService;
import newbbb.service.ICoinService;
import newbbb.service.IRedisService;
import newbbb.service.ITxPairService;
import newbbb.util.UUIdUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TestMain {

    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");

        IRedisService redisService = context.getBean(IRedisService.class);
        ICoinService coinService = context.getBean(ICoinService.class);
        IAccountService accountService = context.getBean(IAccountService.class);
        ITxPairService txPairService = context.getBean(ITxPairService.class);


        // 加载所有币种
        List<Coin> coinList = coinService.getList();
        NBGlobalConfig.COINS = new Coin[coinList.get(coinList.size() - 1).getId() + 1];
        for(Coin coin : coinList){
            NBGlobalConfig.COINS[coin.getId()] = coin;
        }
        // 加载所有交易对
        List<TxPair> txPairList = txPairService.getList();
        NBGlobalConfig.TX_PAIRS = new TxPair[txPairList.get(txPairList.size() - 1).getId() + 1];
        for(TxPair txPair : txPairList){
            NBGlobalConfig.TX_PAIRS[txPair.getId()] = txPair;
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


        // 添加tx_pair数据
        /*long timeNow = new Date().getTime();
        TxPair tp = new TxPair();
        tp.setfCoinId(1);
        tp.setaCoinId(2);
        tp.setPairName("BTC-USDT");
        tp.setCreateTime(timeNow);
        tp.setUpdateTime(timeNow);
        txPairService.add(tp);*/


        // 添加account数据
//        accountService.register(new Account());


    }

    public static void main(String[] args) throws Exception {
        TradingTest instance = context.getBean(TradingTest.class);

        BuyOrder requestOrder = new BuyOrder();
        requestOrder.setTxPairId(1);
        requestOrder.setAccountUid("1b855c98f2b341ae8c1b29728ffc64ab");
        requestOrder.setPrice(new BigDecimal("10"));
        requestOrder.setVolume(new BigDecimal("2"));

        int r = instance.makeTrade(requestOrder, TxDirectionEnum.BUY);
        System.out.println(r);


    }


}
