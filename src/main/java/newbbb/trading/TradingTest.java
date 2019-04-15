package newbbb.trading;

import newbbb.constant.NBGlobalConfig;
import newbbb.enums.AssetUpdateEnum;
import newbbb.enums.TxDirectionEnum;
import newbbb.model.*;
import newbbb.service.IAccountAssetService;
import newbbb.service.IAccountService;
import newbbb.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Date;

@Component("tradingTest")
public class TradingTest {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IAccountService aService;

    @Autowired
    private IAccountAssetService aaService;

    // 1: 成功   2: 资产不足    3: code error
    public int makeTrade(Order requestOrder, TxDirectionEnum txDirectionEnum) {
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
    public int makeBuyTrade(Order requestOrder) {
        TxPair tp = NBGlobalConfig.TX_PAIRS[requestOrder.getTxPairId()];
        // 判断是否存在资产记录
        AccountAsset aa = aaService.getByAUidAndCId(requestOrder.getAccountUid(), tp.getaCoinId());
        if(aa == null){
            return 3;
        }
        // 判断资产是否足够
        BigDecimal oTotalPrice = requestOrder.getPrice().multiply(requestOrder.getVolume());
        if(aa.getAmt().compareTo(oTotalPrice) < 0){
            return 2;
        }

        // 冻结资产
        aaService.updateAmt(aa.getAccountUid(), aa.getCoinId(), oTotalPrice, AssetUpdateEnum.SUB);
        aaService.updateForzenAmt(aa.getAccountUid(), aa.getCoinId(), oTotalPrice, AssetUpdateEnum.SUB);

        // 添加订单记录
        

        // 交易匹配
        long timeNow = new Date().getTime();





        return 3;
    }

    // 卖单
    public int makeSellTrade(Order requestOrder) {

        return 0;
    }

}
