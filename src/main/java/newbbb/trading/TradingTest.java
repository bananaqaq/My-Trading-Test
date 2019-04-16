package newbbb.trading;

import newbbb.constant.NBGlobalConfig;
import newbbb.enums.AssetUpdateEnum;
import newbbb.enums.TxDirectionEnum;
import newbbb.model.*;
import newbbb.service.*;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    // requestOrder需携带参数 1：txPairId     2：accountUid    3：price     4：volume
    // 返回值 1: 成功   2: 资产不足    3: code error
    public int makeTrade(Order requestOrder, TxDirectionEnum txDirectionEnum) throws Exception {
        // 判断requestOrder参数是否正确
        if (requestOrder == null || txDirectionEnum == null || requestOrder.getVolume().compareTo(BigDecimal.ZERO) <= 0 || requestOrder.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return 3;
        }
        switch (txDirectionEnum){
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
        requestOrder.setCreateTime(timeNow);
        requestOrder.setUpdateTime(timeNow);

        TxPair tp = NBGlobalConfig.TX_PAIRS[requestOrder.getTxPairId()];
        // 判断是否存在资产记录
        AccountAsset aa = aaService.getByAUidAndCId(requestOrder.getAccountUid(), tp.getaCoinId());
        if (aa == null) {
            return 3;
        }
        // 判断资产是否足够
        BigDecimal oTotalPrice = requestOrder.getPrice().multiply(requestOrder.getVolume());
        if (aa.getAmt().compareTo(oTotalPrice) < 0) {
            return 2;
        }

        // 冻结资产
        aaService.updateAmt(aa.getAccountUid(), aa.getCoinId(), oTotalPrice, AssetUpdateEnum.SUB);
        aaService.updateForzenAmt(aa.getAccountUid(), aa.getCoinId(), oTotalPrice, AssetUpdateEnum.ADD);

        // 添加订单记录
        boService.add((BuyOrder)requestOrder);

        // 交易匹配
        int flag = 1;
        int maxFlag = 5;
        int limitNum = 1;

        List<SellOrder> soList = soService.getUncompletedList(tp.getId(), limitNum);
        Iterator<SellOrder> soIterator = soList.iterator();

        while (soIterator.hasNext()) {
            SellOrder so = soIterator.next();
            BigDecimal rPrice = requestOrder.getPrice();
            BigDecimal rVolume = requestOrder.getVolume();
            BigDecimal sPrice = so.getPrice();
            BigDecimal sVolume = so.getVolume();

            if(rPrice.compareTo(sPrice) >= 0){
                if(rVolume.compareTo(BigDecimal.ZERO) <= 0){
                    break;
                }
                BigDecimal txPrice = sPrice;
                BigDecimal txVolume = BigDecimal.ZERO;
                BigDecimal txTotalPrice = BigDecimal.ZERO;

                if(rVolume.compareTo(sVolume) >= 0){
                    txVolume = sVolume;
                }else{
                    txVolume = rVolume;
                    soService.subVolume(so.getUid(), txVolume);
                }
                txTotalPrice = txPrice.multiply(txVolume);

                // 更新订单
                requestOrder.subVolume(txVolume);

                // 更新用户资产
                aaService.updateForzenAmt(requestOrder.getAccountUid(), tp.getaCoinId(), txVolume.multiply(rPrice), AssetUpdateEnum.SUB);
                aaService.updateAmt(requestOrder.getAccountUid(), tp.getaCoinId(), txVolume.multiply(rPrice.subtract(sPrice)), AssetUpdateEnum.ADD);
                aaService.updateAmt(requestOrder.getAccountUid(), tp.getfCoinId(), txVolume, AssetUpdateEnum.ADD);

                aaService.updateForzenAmt(so.getAccountUid(), tp.getfCoinId(), txVolume, AssetUpdateEnum.SUB);
                aaService.updateAmt(so.getAccountUid(), tp.getaCoinId(), txTotalPrice, AssetUpdateEnum.ADD);


                // 添加交易记录
                TxRecord txRecord = new TxRecord();
                txRecord.setUid(UUIdUtil.getUUID());
                txRecord.setBuyerUid(requestOrder.getAccountUid());
                txRecord.setSellerUid(so.getAccountUid());
                txRecord.setDealPrice(txPrice);
                txRecord.setVolume(txVolume);
                txRecord.setCreateTime(timeNow);
                txRecord.setUpdateTime(timeNow);
                trService.add(txRecord);
            }else{
                break;
            }


            limitNum = limitNum << (flag <= maxFlag? 1 : 0);
            flag++;
            soList.addAll(soService.getUncompletedList(tp.getId(), limitNum));
        }
        BigDecimal totalTxVolume = requestOrder.getInitialVolume().subtract(requestOrder.getVolume());
        if (totalTxVolume.compareTo(BigDecimal.ZERO) > 0) {
            boService.subVolume(requestOrder.getAccountUid(), totalTxVolume);
        }
        return 1;
    }

    // 卖单
    public int makeSellTrade(Order requestOrder) {

        return 0;
    }

}
