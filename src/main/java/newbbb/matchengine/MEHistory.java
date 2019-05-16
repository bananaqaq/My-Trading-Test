package newbbb.matchengine;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@org.springframework.core.annotation.Order(11)
public class MEHistory {



    @PostConstruct
    private void InitHistory(){

    }

    public int appendAccountBalanceHistory(Long t, Long accountId, String asset, String business, BigDecimal change, String detail){
        return 0;
    }

}
