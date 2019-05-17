package newbbb.matchengine;

import newbbb.matchengine.enums.OrderEventEnum;
import newbbb.matchengine.enums.OrderSideEnum;
import newbbb.model.me.Order;
import newbbb.model.me.TradingMarket;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@org.springframework.core.annotation.Order(4)
public class MEMessage {

    @PostConstruct
    private void InitMessage(){

    }

    public int PushBalanceMsg(long t, long accountId, String asset, String business, BigDecimal change){
        return 0;
    }

    public int PushOrderMsg(OrderEventEnum event, Order order, TradingMarket tm){
        return 0;
    }

    public int PushDealMsg(Long t, String market, Order ask, Order bid, BigDecimal price, BigDecimal amt, BigDecimal askFee, BigDecimal bidFee, OrderSideEnum side, long id, String stock, String money){
        return 0;
    }

    public boolean IsMsgBlock(){
        return false;
    }

}
