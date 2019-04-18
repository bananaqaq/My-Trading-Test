package newbbb.trading;

import newbbb.constant.NBGlobalConfig;
import newbbb.enums.TxDirectionEnum;
import newbbb.model.*;
import newbbb.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.security.jca.GetInstance;

import java.math.BigDecimal;
import java.util.*;

public class TestMain {

    private static ApplicationContext context;

    private static IRedisService redisService;
    private static ICoinService coinService;
    private static IAccountService accountService;
    private static IAccountAssetService accountAssetService;
    private static ITxPairService txPairService;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");

        redisService = context.getBean(IRedisService.class);
        coinService = context.getBean(ICoinService.class);
        accountService = context.getBean(IAccountService.class);
        accountAssetService = context.getBean(IAccountAssetService.class);
        txPairService = context.getBean(ITxPairService.class);


        // 加载所有币种
        List<Coin> coinList = coinService.getList();
        NBGlobalConfig.COINS = new Coin[coinList.get(coinList.size() - 1).getId() + 1];
        for (Coin coin : coinList) {
            NBGlobalConfig.COINS[coin.getId()] = coin;
        }
        // 加载所有交易对
        List<TxPair> txPairList = txPairService.getList();
        NBGlobalConfig.TX_PAIRS = new TxPair[txPairList.get(txPairList.size() - 1).getId() + 1];
        for (TxPair txPair : txPairList) {
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
        /*String accountUid = "1b855c98f2b341ae8c1b29728ffc64ab";
        String testAccountUid = "4bf34b3546ed49389e7c732bfe23cb79";
        int txPairId = 2;

        SellOrder s1 = new SellOrder(txPairId, accountUid, "15", "2");
        SellOrder s2 = new SellOrder(txPairId, accountUid, "14", "2");
        SellOrder s3 = new SellOrder(txPairId, accountUid, "13", "2");
        SellOrder s4 = new SellOrder(txPairId, accountUid, "12", "2");
        SellOrder s5 = new SellOrder(txPairId, accountUid, "11", "2");
        int sr1 = instance.makeTrade(s1, TxDirectionEnum.SELL);
        int sr2 = instance.makeTrade(s2, TxDirectionEnum.SELL);
        int sr3 = instance.makeTrade(s3, TxDirectionEnum.SELL);
        int sr4 = instance.makeTrade(s4, TxDirectionEnum.SELL);
        int sr5 = instance.makeTrade(s5, TxDirectionEnum.SELL);

        BuyOrder b1 = new BuyOrder(txPairId, accountUid, "10", "2");
        BuyOrder b2 = new BuyOrder(txPairId, accountUid, "9", "2");
        BuyOrder b3 = new BuyOrder(txPairId, accountUid, "8", "2");
        BuyOrder b4 = new BuyOrder(txPairId, accountUid, "7", "2");
        BuyOrder b5 = new BuyOrder(txPairId, accountUid, "6", "2");

        int br1 = instance.makeTrade(b1, TxDirectionEnum.BUY);
        int br2 = instance.makeTrade(b2, TxDirectionEnum.BUY);
        int br3 = instance.makeTrade(b3, TxDirectionEnum.BUY);
        int br4 = instance.makeTrade(b4, TxDirectionEnum.BUY);
        int br5 = instance.makeTrade(b5, TxDirectionEnum.BUY);


        for (int i = 0; i < 1000; i++) {
            Scanner sc = new Scanner(System.in);
            msgPrint("please enter opt type( b|s )");
            String txDirectionStr = sc.next();
            msgPrint("please enter a price:");
            String priceStr = sc.next();
            msgPrint("please enter a volume:");
            String volumeStr = sc.next();

            if ("b".equals(txDirectionStr)) {
                BuyOrder bo = new BuyOrder(txPairId, testAccountUid, priceStr, volumeStr);
                instance.makeTrade(bo, TxDirectionEnum.BUY);
            } else {
                SellOrder so = new SellOrder(txPairId, testAccountUid, priceStr, volumeStr);
                instance.makeTrade(so, TxDirectionEnum.SELL);
            }
        }*/


        TxPair tp = NBGlobalConfig.TX_PAIRS[2];
        int userNum = 100;
        int tradingNum = 8;
        int fAmtMax = 1000;
        int aAmtMax = 1000;
        Random random = new Random();
        ArrayList<TradeInfo> oList = new ArrayList<>();

        long startTime = new Date().getTime();

        for (int i = 0; i < userNum; i++) {
            String accountUid = accountService.register(new Account());

            for (int j = 0; j < tradingNum / 2; j++) {
                AccountAsset faa = accountAssetService.getByAUidAndCId(accountUid, tp.getfCoinId());
                if (faa.getAmt().compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                int totalPrice = random.nextInt(aAmtMax / tradingNum) + 10;
                int price = random.nextInt(100) + 1;
                int volume = totalPrice / price + 1;
                System.out.println("B\t" + volume + "\t\t\t" + price + "\t\t\t");
                BuyOrder bo = new BuyOrder(tp.getId(), accountUid, price + "", volume + "");
                oList.add(new TradeInfo(bo, TxDirectionEnum.BUY));
            }
            for (int j = 0; j < tradingNum / 2; j++) {
                AccountAsset aaa = accountAssetService.getByAUidAndCId(accountUid, tp.getaCoinId());
                if (aaa.getAmt().compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                double volume = random.nextInt(fAmtMax / tradingNum) + 2;
                double price = random.nextInt(100) + 1;
                System.out.println("S\t" + volume + "\t\t\t" + price + "\t\t\t");
                SellOrder so = new SellOrder(tp.getId(), accountUid, price + "", volume + "");
                oList.add(new TradeInfo(so, TxDirectionEnum.SELL));
            }
            System.out.println(i + "==================================");
        }

        long middleTime = new Date().getTime();

        System.out.println("data init finish:" + (middleTime - startTime));
        System.out.println("data length:" + oList.size());

        int i = 0;
        int errNum = 0;
        int successNum = 0;
        for (TradeInfo ti : oList) {
            i++;
            long txStartTime = new Date().getTime();
            int r = instance.makeTrade(ti.getOrder(), ti.getTxDirectionEnum());
            long txEndTime = new Date().getTime();
            if (r == 1) {
                successNum++;
            } else {
                errNum++;
            }
            System.out.println(i + ": " + r + ": " + (txEndTime - txStartTime));
        }

        long endTime = new Date().getTime();

        System.out.println("make trade finish:" + (endTime - middleTime));
        System.out.println("successNum:" + successNum);
        System.out.println("errNum:" + errNum);

    }

    private static void msgPrint(String msg) {
        System.out.println(msg);
    }

    private static class TradeInfo {
        Order order;
        TxDirectionEnum txDirectionEnum;

        public TradeInfo(Order order, TxDirectionEnum txDirectionEnum) {
            this.order = order;
            this.txDirectionEnum = txDirectionEnum;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public TxDirectionEnum getTxDirectionEnum() {
            return txDirectionEnum;
        }

        public void setTxDirectionEnum(TxDirectionEnum txDirectionEnum) {
            this.txDirectionEnum = txDirectionEnum;
        }
    }


}
