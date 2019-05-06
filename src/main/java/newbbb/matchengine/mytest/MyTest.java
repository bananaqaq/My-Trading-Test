package newbbb.matchengine.mytest;

import newbbb.matchengine.enums.TxDirectionEnum;
import newbbb.util.UUIdUtil;
import java.math.BigDecimal;
import java.util.*;

public class MyTest {

    public static void main(String[] args) throws Exception {

        int userNum = 10000;
        int tradingNum = 8;
        int fAmtMax = 100;
        int aAmtMax = 10000;

        int volumeMax = (int) (fAmtMax / tradingNum / 1.0);
        int volumeMin = 1;
        int volumeBound = volumeMax - volumeMin;

        int priceMax = (int) (aAmtMax / tradingNum / volumeMax / 1.0);
        int priceMin = (int) (priceMax * 0.8);
        int priceBound = priceMax - priceMin;

        for (int i = 0; i < userNum; i++) {
            UserAssets ua = new UserAssets(UUIdUtil.getUUID(), "10000", "100");
            Trading.uaTable.put(ua.getUserId(), ua);
        }
        Random random = new Random();
        ArrayList<TradeInfo> oList = new ArrayList<>();

        long startTime = new Date().getTime();

        int ii = 1;
        for (Map.Entry<String, UserAssets> entry : Trading.uaTable.entrySet()) {
            String accountUid = entry.getValue().getUserId();
            BigDecimal fAmtTotal = new BigDecimal(fAmtMax);
            BigDecimal aAmtTotal = new BigDecimal(aAmtMax);

            for (int j = 0; j < tradingNum / 2; j++) {
                double price = priceMin + random.nextInt(priceBound * 10) / 10.0;
                double volume = volumeMin + random.nextInt(volumeBound * 10) / 10.0;
                aAmtTotal = aAmtTotal.subtract(new BigDecimal(price * volume));
                if (aAmtTotal.compareTo(BigDecimal.ZERO) < 0) {
                    break;
                }
                System.out.println("B\t" + volume + "\t\t\t" + price + "\t\t\t");
                BuyOrder bo = new BuyOrder(accountUid, price + "", volume + "");
                oList.add(new TradeInfo(bo, TxDirectionEnum.BUY));
            }
            for (int j = 0; j < tradingNum / 2; j++) {
                double price = priceMin + random.nextInt(priceBound * 10) / 10.0;
                double volume = volumeMin + random.nextInt(volumeBound * 10) / 10.0;
                fAmtTotal = fAmtTotal.subtract(new BigDecimal(volume));
                if (fAmtTotal.compareTo(BigDecimal.ZERO) < 0) {
                    break;
                }
                System.out.println("S\t" + volume + "\t\t\t" + price + "\t\t\t");
                SellOrder so = new SellOrder(accountUid, price + "", volume + "");
                oList.add(new TradeInfo(so, TxDirectionEnum.SELL));
            }

            System.out.println((ii++) + "==================================");
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
            int r = Trading.makeTrade(ti.getOrder(), ti.getTxDirectionEnum());
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
        System.out.println("time avg:" + (endTime - middleTime) / (userNum * tradingNum));

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
