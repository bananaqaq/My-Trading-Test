import java.math.BigDecimal;
import java.util.*;

import myEnum.*;

public class Trading {

    public static ArrayList<TxRecord> txRecordList = new ArrayList<>();
    public static Hashtable<String, UserAssets> uaTable = new Hashtable<>();
    public static TreeSet<SellOrder> soTree = new TreeSet<>(
            new Comparator<SellOrder>() {
                @Override
                public int compare(SellOrder o1, SellOrder o2) {
                    BigDecimal p1 = o1.getPrice();
                    BigDecimal p2 = o2.getPrice();
                    int pcp = p1.compareTo(p2);
                    if (p1.compareTo(p2) == 0) {
                        long tct = o1.getCreateTime() - o2.getCreateTime();
                        if (tct > 0) {
                            return 1;
                        } else if (tct == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    } else {
                        return pcp;
                    }
                }
            }
    );
    public static TreeSet<BuyOrder> boTree = new TreeSet<>(
            new Comparator<BuyOrder>() {
                @Override
                public int compare(BuyOrder o1, BuyOrder o2) {
                    BigDecimal p1 = o1.getPrice();
                    BigDecimal p2 = o2.getPrice();
                    int pcp = p1.compareTo(p2);
                    if (p1.compareTo(p2) == 0) {
                        long tct = o1.getCreateTime() - o2.getCreateTime();
                        if (tct > 0) {
                            return 1;
                        } else if (tct == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    } else {
                        return -1 * pcp;
                    }
                }
            }
    );

    public static void main(String[] args) throws Exception {
        UserAssets ua1 = new UserAssets("111", "10000", "1000");
        UserAssets ua2 = new UserAssets("222", "10000", "1000");

        uaTable.put(ua1.getUserId(), ua1);
        uaTable.put(ua2.getUserId(), ua2);

        SellOrder so6 = new SellOrder(ua1.getUserId(), "16", "10");
        SellOrder so5 = new SellOrder(ua1.getUserId(), "15", "10");
        SellOrder so4 = new SellOrder(ua1.getUserId(), "14", "10");
        SellOrder so3 = new SellOrder(ua1.getUserId(), "13", "10");
        SellOrder so2 = new SellOrder(ua1.getUserId(), "12", "10");
        SellOrder so1 = new SellOrder(ua1.getUserId(), "11", "10");

        BuyOrder bo1 = new BuyOrder(ua1.getUserId(), "10", "10");
        BuyOrder bo2 = new BuyOrder(ua1.getUserId(), "9", "10");
        BuyOrder bo3 = new BuyOrder(ua1.getUserId(), "8", "10");
        BuyOrder bo4 = new BuyOrder(ua1.getUserId(), "7", "10");
        BuyOrder bo5 = new BuyOrder(ua1.getUserId(), "6", "10");
        BuyOrder bo6 = new BuyOrder(ua1.getUserId(), "5", "10");

//        BuyOrder bo1 = new BuyOrder(ua1.getUserId(), "11", "10");
//        BuyOrder bo2 = new BuyOrder(ua1.getUserId(), "12", "10");
//        BuyOrder bo3 = new BuyOrder(ua1.getUserId(), "13", "10");
//        BuyOrder bo4 = new BuyOrder(ua1.getUserId(), "14", "10");
//        BuyOrder bo5 = new BuyOrder(ua1.getUserId(), "15", "10");
//        BuyOrder bo6 = new BuyOrder(ua1.getUserId(), "16", "10");


        makeTrade(so6, TxDirectionEnum.SELL);
        makeTrade(so5, TxDirectionEnum.SELL);
        makeTrade(so4, TxDirectionEnum.SELL);
        makeTrade(so3, TxDirectionEnum.SELL);
        makeTrade(so2, TxDirectionEnum.SELL);
        makeTrade(so1, TxDirectionEnum.SELL);

        makeTrade(bo1, TxDirectionEnum.BUY);
        makeTrade(bo2, TxDirectionEnum.BUY);
        makeTrade(bo3, TxDirectionEnum.BUY);
        makeTrade(bo4, TxDirectionEnum.BUY);
        makeTrade(bo5, TxDirectionEnum.BUY);
        makeTrade(bo6, TxDirectionEnum.BUY);


        for (int i = 0; i < 1000; i++) {
            msgPrint(ua1.toString());
            msgPrint(ua2.toString());
            msgPrint("");
            for (SellOrder so : soTree) {
                msgPrint(so.getPrice() + "\t\t" + so.getVolume() + "\t\t" + so.getUserId());
            }
            msgPrint("||||||||||||||||||||");
            for (BuyOrder bo : boTree) {
                msgPrint(bo.getPrice() + "\t\t" + bo.getVolume() + "\t\t" + bo.getUserId());
            }

            Scanner sc = new Scanner(System.in);
            msgPrint("please enter opt type( b|s )");
            String txDirectionStr = sc.next();
            msgPrint("please enter a price:");
            String priceStr = sc.next();
            msgPrint("please enter a volume:");
            String volumeStr = sc.next();

            if("b".equals(txDirectionStr)){
                BuyOrder requestOrder = new BuyOrder(ua2.getUserId(), priceStr, volumeStr);
                makeTrade(requestOrder, TxDirectionEnum.BUY);
            }else{
                SellOrder requestOrder = new SellOrder(ua2.getUserId(), priceStr, volumeStr);
                makeTrade(requestOrder, TxDirectionEnum.SELL);
            }


        }

    }

    public static void msgPrint(String msg){
        System.out.println(msg);
    }

    // 1: 成功   2: 资产不足    3: code error
    public static int makeTrade(Order requestOrder, TxDirectionEnum txDirectionEnum) throws Exception {
        if(requestOrder.getVolume().compareTo(BigDecimal.ZERO) <= 0 || requestOrder.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            return 3;
        }
        // search user
        UserAssets ua = uaTable.get(requestOrder.getUserId());
        long timeNow = new Date().getTime();

        switch (txDirectionEnum) {
            case BUY:
                // 判断资产是否足够
                BigDecimal oTotalPrice = requestOrder.getPrice().multiply(requestOrder.getVolume());
                if (ua.getUsdtAmt().compareTo(oTotalPrice) < 0) {
                    return 2;
                }

                // 冻结资产
                ua.updateUsdtAmt(oTotalPrice, AssetsUpateEnum.SUB);
                ua.updateUsdtForzenAmt(oTotalPrice, AssetsUpateEnum.ADD);

                // 交易匹配
                Iterator<SellOrder> soIterator = soTree.iterator();
                while (soIterator.hasNext()) {
                    SellOrder so = soIterator.next();
                    BigDecimal rPrice = requestOrder.getPrice();
                    BigDecimal rVolume = requestOrder.getVolume();
                    BigDecimal sPrice = so.getPrice();
                    BigDecimal sVolume = so.getVolume();

                    if (rPrice.compareTo(sPrice) >= 0) {
                        UserAssets sellUa = uaTable.get(so.getUserId());
                        BigDecimal txPrice = sPrice;
                        BigDecimal txVolume = BigDecimal.ZERO;
                        BigDecimal txTotalPrice = BigDecimal.ZERO;

                        if (rVolume.compareTo(sVolume) >= 0) {
                            txVolume = sVolume;
                            soIterator.remove();
                        } else {
                            txVolume = rVolume;
                            so.subVolume(txVolume);
                            so.setUpdateTime(timeNow);
                        }
                        txTotalPrice = txPrice.multiply(txVolume);

                        // 更新订单
                        requestOrder.subVolume(txVolume);

                        // 更新用户资产
                        ua.updateUsdtForzenAmt(txVolume.multiply(rPrice), AssetsUpateEnum.SUB);
                        ua.updateUsdtAmt(txVolume.multiply(rPrice.subtract(sPrice)), AssetsUpateEnum.ADD);
                        ua.updateBtcAmt(txVolume, AssetsUpateEnum.ADD);
                        sellUa.updateUsdtAmt(txTotalPrice, AssetsUpateEnum.ADD);
                        sellUa.updateBtcForzenAmt(txVolume, AssetsUpateEnum.SUB);

                        // 添加交易记录
                        TxRecord txRecord = new TxRecord(ua.getUserId(), sellUa.getUserId(), txPrice.toString(), txVolume.toString(), timeNow);
                        txRecordList.add(txRecord);

                        if (rVolume.compareTo(sVolume) <= 0) {
                            break;
                        }
                    }
                }
                if (requestOrder.getVolume().compareTo(BigDecimal.ZERO) > 0) {
                    boTree.add((BuyOrder) requestOrder);
                }
                break;

            case SELL:
                // 判断资产是否足够
                BigDecimal oVolume = requestOrder.getVolume();
                if (ua.getBtcAmt().compareTo(oVolume) < 0) {
                    return 2;
                }

                // 冻结资产
                ua.updateBtcAmt(oVolume, AssetsUpateEnum.SUB);
                ua.updateBtcForzenAmt(oVolume, AssetsUpateEnum.ADD);

                // 交易匹配
                Iterator<BuyOrder> boIterable = boTree.iterator();
                while (boIterable.hasNext()) {
                    BuyOrder bo = boIterable.next();
                    BigDecimal rPrice = requestOrder.getPrice();
                    BigDecimal rVolume = requestOrder.getVolume();
                    BigDecimal bPrice = bo.getPrice();
                    BigDecimal bVolume = bo.getVolume();

                    if (rPrice.compareTo(bPrice) <= 0) {
                        UserAssets buyUa = uaTable.get(bo.getUserId());
                        BigDecimal txPrice = bPrice;
                        BigDecimal txVolume = BigDecimal.ZERO;
                        BigDecimal txTotalPrice = BigDecimal.ZERO;

                        if (rVolume.compareTo(bVolume) >= 0) {
                            txVolume = bVolume;
                            boIterable.remove();
                        } else {
                            txVolume = rVolume;
                            bo.subVolume(txVolume);
                            bo.setUpdateTime(timeNow);
                        }

                        // 更新订单
                        requestOrder.subVolume(txVolume);

                        // 更新用户资产
                        ua.updateBtcForzenAmt(txVolume, AssetsUpateEnum.SUB);
                        ua.updateUsdtAmt(txTotalPrice, AssetsUpateEnum.ADD);
                        buyUa.updateBtcAmt(txVolume, AssetsUpateEnum.ADD);
                        buyUa.updateUsdtForzenAmt(txTotalPrice, AssetsUpateEnum.SUB);

                        // 添加交易记录
                        TxRecord txRecord = new TxRecord(buyUa.getUserId(), ua.getUserId(), txPrice.toString(), txVolume.toString(), timeNow);

                        if(rVolume.compareTo(bVolume) <= 0){
                            break;
                        }
                    }
                }

                if (requestOrder.getVolume().compareTo(BigDecimal.ZERO) > 0) {
                    soTree.add((SellOrder) requestOrder);
                }
                break;
            default:
                return 3;
        }

        return 1;
    }




}
