package newbbb.trading;

import newbbb.abq.AssetUpdateAbq;
import newbbb.abq.TxAddAbq;
import newbbb.abq.model.AssetUpdateInfo;
import newbbb.constant.NBGlobalConfig;
import newbbb.enums.AssetTypeEnum;
import newbbb.enums.AssetUpdateEnum;
import newbbb.enums.TxDirectionEnum;
import newbbb.model.*;
import newbbb.service.*;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component("tradingTest")
public class TradingTest{

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IAccountService aService;

    @Autowired
    private IAccountAssetService aaService;

    @Autowired
    private IBuyOrderService boService;

    @Autowired
    private ISellOrderService soService;

    @Autowired
    private ITxRecordService trService;

    @Autowired
    private AssetUpdateAbq auAbq;

    @Autowired
    private TxAddAbq taAbq;

    @PostConstruct
    private void initAbqHandle(){
        Thread auThread = new Thread(auAbq);
        Thread taThread = new Thread(taAbq);

        auThread.start();
//        taThread.start();
    }

    // requestOrder需携带参数 1：txPairId     2：accountUid    3：price     4：volume
    // 返回值 1: 成功   2: 失败    3: code error
    public int makeTrade(Order requestOrder, TxDirectionEnum txDirectionEnum) throws Exception {
        // 判断requestOrder参数是否正确
        if (requestOrder == null || txDirectionEnum == null || requestOrder.getVolume().compareTo(BigDecimal.ZERO) <= 0 || requestOrder.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return 4;
        }
        switch (txDirectionEnum) {
            case BUY:
                return makeBuyTrade(requestOrder);
            case SELL:
                return makeSellTrade(requestOrder);
            default:
                break;
        }
        return 3;
    }

    // 买单
    public int makeBuyTrade(Order requestOrder) throws Exception {
        long timeNow = new Date().getTime();
        TxPair tp = NBGlobalConfig.TX_PAIRS[requestOrder.getTxPairId()];
        String accountUid = requestOrder.getAccountUid();
        int fCoinId = tp.getfCoinId();
        int aCoinId = tp.getaCoinId();
        BigDecimal oTotalPrice = requestOrder.getPrice().multiply(requestOrder.getVolume());

        //冻结资产
        if (aaService.forzenAsset(accountUid, aCoinId, oTotalPrice) == 0) {
            return 2;
        }

        // 添加订单记录
        boService.add((BuyOrder) requestOrder);

        // 交易匹配
        int flag = 0;
        int maxFlag = 5;
        int limitNum = 1;

        long startTime = new Date().getTime();
        List<SellOrder> soList = soService.getUncompletedList(tp.getId(), limitNum);
        long midTime = new Date().getTime();
        Iterator<SellOrder> soIterator = soList.iterator();


        AssetUpdateInfo aui1 = new AssetUpdateInfo(requestOrder.getAccountUid(), aCoinId, BigDecimal.ZERO, AssetUpdateEnum.SUB, AssetTypeEnum.FORZEN);
        AssetUpdateInfo aui2 = new AssetUpdateInfo(requestOrder.getAccountUid(), aCoinId, BigDecimal.ZERO, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);
        AssetUpdateInfo aui3 = new AssetUpdateInfo(requestOrder.getAccountUid(), fCoinId, BigDecimal.ZERO, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);

        if (soIterator.hasNext()) {
            while (true) {
                if (!soIterator.hasNext()) {
                    limitNum = limitNum << (flag <= maxFlag ? 1 : 0);
                    flag++;
                    soList = soService.getUncompletedList(tp.getId(), limitNum);
                    soIterator = soList.iterator();
                    if (!soIterator.hasNext()) {
                        break;
                    }
                }

                SellOrder so = soIterator.next();
                BigDecimal rPrice = requestOrder.getPrice();
                BigDecimal rVolume = requestOrder.getVolume();
                BigDecimal sPrice = so.getPrice();
                BigDecimal sVolume = so.getVolume();
                BigDecimal txPrice = sPrice;
                BigDecimal txVolume = BigDecimal.ZERO;
                BigDecimal txTotalPrice = BigDecimal.ZERO;

                if (rPrice.compareTo(sPrice) < 0) {
                    break;
                }

                txVolume = rVolume.compareTo(sVolume) < 0 ? rVolume : sVolume;
                txTotalPrice = txPrice.multiply(txVolume);

                // 更新订单
                requestOrder.subVolume(txVolume);
                soService.subVolume(so.getUid(), txVolume);

                // 更新用户资产
//                aaService.updateForzenAmt(requestOrder.getAccountUid(), aCoinId, txVolume.multiply(rPrice), AssetUpdateEnum.SUB);
//                aaService.updateAmt(requestOrder.getAccountUid(), aCoinId, txVolume.multiply(rPrice.subtract(sPrice)), AssetUpdateEnum.ADD);
//                aaService.updateAmt(requestOrder.getAccountUid(), fCoinId, txVolume, AssetUpdateEnum.ADD);
                aui1.setVolume(aui1.getVolume().add(txVolume.multiply(rPrice)));
                aui2.setVolume(aui2.getVolume().add(txVolume.multiply(rPrice.subtract(sPrice))));
                aui3.setVolume(aui3.getVolume().add(txVolume));

//                aaService.updateForzenAmt(so.getAccountUid(), fCoinId, txVolume, AssetUpdateEnum.SUB);
//                aaService.updateAmt(so.getAccountUid(), aCoinId, txTotalPrice, AssetUpdateEnum.ADD);
                AssetUpdateInfo aui4 = new AssetUpdateInfo(so.getAccountUid(), fCoinId, txVolume, AssetUpdateEnum.SUB, AssetTypeEnum.FORZEN);
                AssetUpdateInfo aui5 = new AssetUpdateInfo(so.getAccountUid(), aCoinId, txTotalPrice, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);
                auAbq.put(aui4);
                auAbq.put(aui5);

                // 添加交易记录
                TxRecord txRecord = new TxRecord();
                txRecord.setUid(UUIdUtil.getUUID());
                txRecord.setBuyerUid(requestOrder.getAccountUid());
                txRecord.setSellerUid(so.getAccountUid());
                txRecord.setDealPrice(txPrice);
                txRecord.setVolume(txVolume);
                txRecord.setTxFee(BigDecimal.ZERO);
                txRecord.setCreateTime(timeNow);
                txRecord.setUpdateTime(timeNow);
                trService.add(txRecord);

                if (rVolume.compareTo(sVolume) < 0) {
                    break;
                }
            }
        }
        long endTime = new Date().getTime();
        BigDecimal totalTxVolume = requestOrder.getInitialVolume().subtract(requestOrder.getVolume());
        if (totalTxVolume.compareTo(BigDecimal.ZERO) > 0) {
            boService.subVolume(requestOrder.getUid(), totalTxVolume);
        }
        if (aui1.getVolume().compareTo(BigDecimal.ZERO) > 0) {
            auAbq.put(aui1);
            auAbq.put(aui2);
            auAbq.put(aui3);
        }
        System.out.println("\tsearch:" + (midTime - startTime));
        System.out.println("\tdeal:" + (endTime - midTime));
        return 1;
    }

    // 卖单
    public int makeSellTrade(Order requestOrder) throws Exception {
        long timeNow = new Date().getTime();
        TxPair tp = NBGlobalConfig.TX_PAIRS[requestOrder.getTxPairId()];
        String accountUid = requestOrder.getAccountUid();
        int fCoinId = tp.getfCoinId();
        int aCoinId = tp.getaCoinId();

        // 冻结资产
        if (aaService.forzenAsset(accountUid, fCoinId, requestOrder.getVolume()) == 0) {
            return 2;
        }

        // 添加订单记录
        soService.add((SellOrder) requestOrder);

        // 交易匹配
        int flag = 1;
        int maxFlag = 5;
        int limitNum = 1;

        long startTime = new Date().getTime();
        List<BuyOrder> boList = boService.getUncompletedList(tp.getId(), limitNum);
        long midTime = new Date().getTime();
        Iterator<BuyOrder> boIterator = boList.iterator();


        AssetUpdateInfo aui1 = new AssetUpdateInfo(requestOrder.getAccountUid(), fCoinId, BigDecimal.ZERO, AssetUpdateEnum.SUB, AssetTypeEnum.FORZEN);
        AssetUpdateInfo aui2 = new AssetUpdateInfo(requestOrder.getAccountUid(), aCoinId, BigDecimal.ZERO, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);

        if (boIterator.hasNext()) {
            while (true) {
                if (!boIterator.hasNext()) {
                    limitNum = limitNum << (flag <= maxFlag ? 1 : 0);
                    flag++;
                    boList = boService.getUncompletedList(tp.getId(), limitNum);
                    boIterator = boList.iterator();
                    if (!boIterator.hasNext()) {
                        break;
                    }
                }

                BuyOrder bo = boIterator.next();
                BigDecimal rPrice = requestOrder.getPrice();
                BigDecimal rVolume = requestOrder.getVolume();
                BigDecimal bPrice = bo.getPrice();
                BigDecimal bVolume = bo.getVolume();
                BigDecimal txPrice = bPrice;
                BigDecimal txVolume = BigDecimal.ZERO;
                BigDecimal txTotalPrice = BigDecimal.ZERO;

                if (rPrice.compareTo(bPrice) > 0) {
                    break;
                }
                txVolume = rVolume.compareTo(bVolume) < 0 ? rVolume : bVolume;
                txTotalPrice = txPrice.multiply(txVolume);

                // 更新订单
                requestOrder.subVolume(txVolume);
                boService.subVolume(bo.getUid(), txVolume);

                // 更新用户资产
//                aaService.updateForzenAmt(requestOrder.getAccountUid(), fCoinId, txVolume, AssetUpdateEnum.SUB);
//                aaService.updateAmt(requestOrder.getAccountUid(), aCoinId, txTotalPrice, AssetUpdateEnum.ADD);
                aui1.setVolume(aui1.getVolume().add(txVolume));
                aui2.setVolume(aui2.getVolume().add(txTotalPrice));

//                aaService.updateForzenAmt(bo.getAccountUid(), aCoinId, txTotalPrice, AssetUpdateEnum.SUB);
//                aaService.updateAmt(bo.getAccountUid(), fCoinId, txVolume, AssetUpdateEnum.ADD);
                AssetUpdateInfo aui3 = new AssetUpdateInfo(bo.getAccountUid(), aCoinId, txTotalPrice, AssetUpdateEnum.SUB, AssetTypeEnum.FORZEN);
                AssetUpdateInfo aui4 = new AssetUpdateInfo(bo.getAccountUid(), fCoinId, txVolume, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);
                auAbq.put(aui3);
                auAbq.put(aui4);

                // 添加交易记录
                TxRecord txRecord = new TxRecord();
                txRecord.setUid(UUIdUtil.getUUID());
                txRecord.setBuyerUid(requestOrder.getAccountUid());
                txRecord.setSellerUid(bo.getAccountUid());
                txRecord.setDealPrice(txPrice);
                txRecord.setVolume(txVolume);
                txRecord.setTxFee(BigDecimal.ZERO);
                txRecord.setCreateTime(timeNow);
                txRecord.setUpdateTime(timeNow);
                trService.add(txRecord);

                if (rVolume.compareTo(bVolume) < 0) {
                    break;
                }
            }
        }
        long endTime = new Date().getTime();
        BigDecimal totalTxVolume = requestOrder.getInitialVolume().subtract(requestOrder.getVolume());
        if (totalTxVolume.compareTo(BigDecimal.ZERO) > 0) {
            soService.subVolume(requestOrder.getUid(), totalTxVolume);
        }
        if(aui1.getVolume().compareTo(BigDecimal.ZERO) > 0){
            auAbq.put(aui1);
            auAbq.put(aui2);
        }
        System.out.println("\tsearch:" + (midTime - startTime));
        System.out.println("\tdeal:" + (endTime - midTime));
        return 1;
    }

}
