import myEnum.TxDirectionEnum;

import java.math.BigDecimal;
import java.util.Random;

public class MyTest {

    public static void main(String[] args) throws Exception {

        int userNum = 10000;
        int tradingNum = 10;
        Random random = new Random();

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
                Trading.makeTrade(bo, TxDirectionEnum.BUY);
            }
            for (int j = 0; j < tradingNum / 2; j++) {
                if (ua.getBtcAmt().compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                double volume = random.nextInt(1000/tradingNum) + 2;
                double price = random.nextInt(100) + 1;
                System.out.println("S\t" + volume + "\t\t\t" + price + "\t\t\t" + ua.getUsdtAmt());
                SellOrder so = new SellOrder(ua.getUserId(), price + "", volume + "");
                Trading.makeTrade(so, TxDirectionEnum.SELL);
            }
            System.out.println(i + "==================================");
        }

    }

}
