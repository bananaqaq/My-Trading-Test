package newbbb.trading;

import com.alibaba.fastjson.JSON;
import newbbb.abq.AssetUpdateAbq;
import newbbb.abq.OrderVolumeSubAbq;
import newbbb.abq.TxAddAbq;
import newbbb.abq.model.AssetUpdateInfo;
import newbbb.abq.model.OrderVolumeSubInfo;
import newbbb.constant.NBGlobalConfig;
import newbbb.enums.AssetTypeEnum;
import newbbb.enums.AssetUpdateEnum;
import newbbb.enums.OrderTypeEnum;
import newbbb.enums.TxDirectionEnum;
import newbbb.model.*;
import newbbb.service.*;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import static newbbb.constant.NBGlobalConfig.*;

@Component("tradingTest")
public class TradingTest {

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

    @Autowired
    private OrderVolumeSubAbq ovsAbq;

    @PostConstruct
    private void initAbqHandle() {
        Thread auThread = new Thread(auAbq);
        Thread taThread = new Thread(taAbq);
        Thread ovsThread = new Thread(ovsAbq);

        auThread.start();
        taThread.start();
        ovsThread.start();
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
        requestOrder = boService.add((BuyOrder) requestOrder);
        addOrder(OrderTypeEnum.BUY, tp.getId(), requestOrder);

        // 交易匹配
        int flag = 0;
        int maxFlag = 5;
        int limitNum = 1;

        long startTime = new Date().getTime();
        List<Order> soList = getUncompletedList(OrderTypeEnum.SELL, tp.getId(), limitNum);
        if (soList == null) {
            return 1;
        }
        long midTime = new Date().getTime();
        Iterator<Order> soIterator = soList.iterator();

        AssetUpdateInfo aui1 = new AssetUpdateInfo(requestOrder.getAccountUid(), aCoinId, BigDecimal.ZERO, AssetUpdateEnum.SUB, AssetTypeEnum.FORZEN);
        AssetUpdateInfo aui2 = new AssetUpdateInfo(requestOrder.getAccountUid(), aCoinId, BigDecimal.ZERO, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);
        AssetUpdateInfo aui3 = new AssetUpdateInfo(requestOrder.getAccountUid(), fCoinId, BigDecimal.ZERO, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);

        while (soIterator.hasNext()) {

            Order so = soIterator.next();
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
            OrderVolumeSubInfo ovsi = new OrderVolumeSubInfo(so.getUid(), txVolume, OrderTypeEnum.SELL);
            ovsAbq.put(ovsi);

            // 更新用户资产
            aui1.addVolume(txVolume.multiply(rPrice));
            aui2.addVolume(txVolume.multiply(rPrice.subtract(sPrice)));
            aui3.addVolume(txVolume);

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
            taAbq.put(txRecord);

            if (rVolume.compareTo(sVolume) < 0) {
                removeOrder(OrderTypeEnum.BUY, tp.getId(), requestOrder.getUid());
                so.subVolume(txVolume);
                subVolume(OrderTypeEnum.SELL, tp.getId(), so.getUid(), so);
                break;
            } else if (rVolume.compareTo(sVolume) == 0) {
                removeOrder(OrderTypeEnum.BUY, tp.getId(), requestOrder.getUid());
                removeOrder(OrderTypeEnum.SELL, tp.getId(), so.getUid());
                break;
            } else {
                subVolume(OrderTypeEnum.BUY, tp.getId(), requestOrder.getUid(), requestOrder);
                removeOrder(OrderTypeEnum.SELL, tp.getId(), so.getUid());
            }
            if (soIterator.hasNext()) {
                limitNum = limitNum << (flag <= maxFlag ? 1 : 0);
                flag++;
                soList = getUncompletedList(OrderTypeEnum.BUY, tp.getId(), limitNum);
                soIterator = soList.iterator();
            }
        }
        long endTime = new Date().getTime();
        BigDecimal totalTxVolume = requestOrder.getInitialVolume().subtract(requestOrder.getVolume());
        if (totalTxVolume.compareTo(BigDecimal.ZERO) > 0) {
            OrderVolumeSubInfo ovsi = new OrderVolumeSubInfo(requestOrder.getUid(), totalTxVolume, OrderTypeEnum.BUY);
            ovsAbq.put(ovsi);
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
        requestOrder = soService.add((SellOrder) requestOrder);
        addOrder(OrderTypeEnum.SELL, tp.getId(), requestOrder);

        // 交易匹配
        int flag = 1;
        int maxFlag = 5;
        int limitNum = 1;

        long startTime = new Date().getTime();
        List<Order> boList = getUncompletedList(OrderTypeEnum.BUY, tp.getId(), limitNum);
        if (boList == null) {
            return 1;
        }
        long midTime = new Date().getTime();
        Iterator<Order> boIterator = boList.iterator();

        AssetUpdateInfo aui1 = new AssetUpdateInfo(requestOrder.getAccountUid(), fCoinId, BigDecimal.ZERO, AssetUpdateEnum.SUB, AssetTypeEnum.FORZEN);
        AssetUpdateInfo aui2 = new AssetUpdateInfo(requestOrder.getAccountUid(), aCoinId, BigDecimal.ZERO, AssetUpdateEnum.ADD, AssetTypeEnum.NORMAL);

        while (boIterator.hasNext()) {

            Order bo = boIterator.next();
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
            OrderVolumeSubInfo ovsi = new OrderVolumeSubInfo(bo.getUid(), txVolume, OrderTypeEnum.BUY);
            ovsAbq.put(ovsi);

            // 更新用户资产
            aui1.addVolume(txVolume);
            aui2.addVolume(txTotalPrice);

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
            taAbq.put(txRecord);

            if (rVolume.compareTo(bVolume) < 0) {
                removeOrder(OrderTypeEnum.SELL, tp.getId(), requestOrder.getUid());
                bo.subVolume(txVolume);
                subVolume(OrderTypeEnum.BUY, tp.getId(), bo.getUid(), bo);
                break;
            } else if (rVolume.compareTo(bVolume) == 0) {
                removeOrder(OrderTypeEnum.SELL, tp.getId(), requestOrder.getUid());
                removeOrder(OrderTypeEnum.BUY, tp.getId(), bo.getUid());
                break;
            } else {
                subVolume(OrderTypeEnum.SELL, tp.getId(), requestOrder.getUid(), requestOrder);
                removeOrder(OrderTypeEnum.BUY, tp.getId(), bo.getUid());
            }
            if (!boIterator.hasNext()) {
                limitNum = limitNum << (flag <= maxFlag ? 1 : 0);
                flag++;
                boList = getUncompletedList(OrderTypeEnum.BUY, tp.getId(), limitNum);
                boIterator = boList.iterator();
            }
        }
        long endTime = new Date().getTime();
        BigDecimal totalTxVolume = requestOrder.getInitialVolume().subtract(requestOrder.getVolume());
        if (totalTxVolume.compareTo(BigDecimal.ZERO) > 0) {
            OrderVolumeSubInfo ovsi = new OrderVolumeSubInfo(requestOrder.getUid(), totalTxVolume, OrderTypeEnum.SELL);
            ovsAbq.put(ovsi);
        }
        if (aui1.getVolume().compareTo(BigDecimal.ZERO) > 0) {
            auAbq.put(aui1);
            auAbq.put(aui2);
        }
        System.out.println("\tsearch:" + (midTime - startTime));
        System.out.println("\tdeal:" + (endTime - midTime));
        return 1;
    }

    private List<Order> getUncompletedList(OrderTypeEnum orderTypeEnum, int txPairId, long count) {
        String[] keyArr = getKey(orderTypeEnum, txPairId);
        String marketKey = keyArr[0];
        String marketDetailKey = keyArr[1];

        Set<String> sortedUidSet = redisService.zRange(marketKey, count);
        if (sortedUidSet.size() == 0) {
            return null;
        }
        List<String> orderJsonList = redisService.hMultiGet(marketDetailKey, sortedUidSet);
        if (orderJsonList.size() == 0) {
            return null;
        }
        List<Order> orderList = new ArrayList<>();
        for (String orderJson : orderJsonList) {
            orderList.add(JSON.parseObject(orderJson, Order.class));
        }

        return orderList;
    }

    private void addOrder(OrderTypeEnum orderTypeEnum, int txPairId, Order order) {
        double score = orderTypeEnum == OrderTypeEnum.BUY ? order.getPrice().doubleValue() * -1 : order.getPrice().doubleValue();
        String[] keyArr = getKey(orderTypeEnum, txPairId);
        String marketKey = keyArr[0];
        String marketDetailKey = keyArr[1];

        String jsonStr = JSON.toJSONString(order);
        redisService.zAdd(marketKey, order.getUid(), score);
        redisService.hSet(marketDetailKey, order.getUid(), jsonStr);
    }

    private void removeOrder(OrderTypeEnum orderTypeEnum, int txPairId, String uid) {
        String[] keyArr = getKey(orderTypeEnum, txPairId);
        String marketKey = keyArr[0];
        String marketDetailKey = keyArr[1];

        redisService.zRemove(marketKey, uid);
        redisService.hRemove(marketDetailKey, uid);
    }

    private void subVolume(OrderTypeEnum orderTypeEnum, int txPairId, String uid, Order order) {
        String[] keyArr = getKey(orderTypeEnum, txPairId);
        String marketKey = keyArr[0];
        String marketDetailKey = keyArr[1];
        String jsonStr = JSON.toJSONString(order);

        redisService.hSet(marketDetailKey, uid, jsonStr);
    }

    private String[] getKey(OrderTypeEnum orderTypeEnum, int txPairId) {
        String[] keyArr = new String[2];
        keyArr[0] = txPairId + "";
        keyArr[1] = txPairId + "";
        switch (orderTypeEnum) {
            case BUY:
                keyArr[0] += MARKET_BUY_SUFFIX;
                keyArr[1] += MARKET_BUY_DETAIL_SUFFIX;
                break;
            case SELL:
                keyArr[0] += MARKET_SELL_SUFFIX;
                keyArr[1] += MARKET_SELL_DETAIL_SUFFIX;
                break;
            default:
                break;
        }
        return keyArr;
    }

}
