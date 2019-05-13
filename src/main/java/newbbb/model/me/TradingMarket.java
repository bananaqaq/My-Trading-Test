package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import newbbb.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TradingMarket {

    private String name;
    private String stock;
    private String money;
    private Integer stockPrec;
    private Integer moneyPrec;
    private Integer feePrec;
    private BigDecimal minAmount;

    private HashMap<Long, Order> orders;
    private HashMap<Long, Account> accounts;
    private ConcurrentSkipListSet<Order> asks;
    private ConcurrentSkipListSet<Order> bids;

}
