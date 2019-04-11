package newbbb.mytest;

import myEnum.TxDirectionEnum;
import newbbb.mytest.BuyOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MyTest {

    public static void main(String[] args) throws Exception {

        int userNum = 10000;
        int tradingNum = 16;
        Random random = new Random();
        ArrayList<TradeInfo> oList = new ArrayList<>();

        long startTime = new Date().getTime();

        for (int i = 0; i < userNum; i++) {
            UserAssets ua = new UserAssets(i + "", "100000", "1000");
            Trading.uaTable.put(ua.getUserId(), ua);
            for (int j = 0; j < tradingNum / 2; j++) {
                if (ua.getUsdtAmt().compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                int totalPrice = random.nextInt(100000/tradingNum) + 10;
                int volume = random.nextInt(100) + 1;
                int price = totalPrice / volume;
                System.out.println("B\t" + volume + "\t\t\t" + price + "\t\t\t" + ua.getUsdtAmt());
                BuyOrder bo = new BuyOrder(ua.getUserId(), price + "", volume + "");
                oList.add(new TradeInfo(bo, TxDirectionEnum.BUY));
            }
            for (int j = 0; j < tradingNum / 2; j++) {
                if (ua.getBtcAmt().compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                double volume = random.nextInt(1000/tradingNum) + 2;
                double price = random.nextInt(100) + 1;
                System.out.println("S\t" + volume + "\t\t\t" + price + "\t\t\t" + ua.getUsdtAmt());
                SellOrder so = new SellOrder(ua.getUserId(), price + "", volume + "");
                oList.add(new TradeInfo(so, TxDirectionEnum.SELL));
            }
            System.out.println(i + "==================================");
        }

        long middleTime = new Date().getTime();

        System.out.println("data init finish:" + (middleTime - startTime));
        System.out.println("data length:" + oList.size());

        for(TradeInfo ti : oList){
            Trading.makeTrade(ti.getOrder(), ti.getTxDirectionEnum());
        }

        long endTime = new Date().getTime();

        System.out.println("make trade finish:" + (endTime - middleTime));

    }

    private static class TradeInfo{
        Order order;
        TxDirectionEnum txDirectionEnum;

        public TradeInfo(Order order, TxDirectionEnum txDirectionEnum){
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
