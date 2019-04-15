package newbbb.trading;

import newbbb.enums.TxDirectionEnum;
import newbbb.model.BuyOrder;
import newbbb.model.Order;
import newbbb.model.SellOrder;
import newbbb.service.IAccountService;
import newbbb.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("tradingTest")
public class TradingTest {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IAccountService accountService;

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

        return 0;
    }

    // 卖单
    public int makeSellTrade(Order requestOrder) {

        return 0;
    }

}
