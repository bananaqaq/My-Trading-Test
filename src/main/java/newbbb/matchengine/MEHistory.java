package newbbb.matchengine;

import newbbb.matchengine.enums.MarketRoleEnum;
import newbbb.model.me.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@org.springframework.core.annotation.Order(3)
public class MEHistory {



    @PostConstruct
    private void InitHistory(){

    }

    public int appendOrderHistory(Order order){
        return 0;
    }

    public int appendOrderDealHistory(Long t, Long dealId, Order ask, MarketRoleEnum askRole, Order bid, MarketRoleEnum bidRole, BigDecimal price, BigDecimal amt, BigDecimal deal, BigDecimal askFee, BigDecimal bidFee){
        return 0;
    }

    public int appendAccountBalanceHistory(Long t, Long accountId, String asset, String business, BigDecimal change, String detail){
        return 0;
    }

    public boolean isHistoryBlock(){
        return true;
    }

}
