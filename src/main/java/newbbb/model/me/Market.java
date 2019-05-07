package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import newbbb.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeSet;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Market {

    private String name;
    private String stock;
    private String money;
    private Integer stockPrec;
    private Integer moneyPrec;
    private Integer feePrec;
    private BigDecimal minAmount;
    private HashMap<Integer, Order> orders;
    private HashMap<Integer, Account> accounts;
    private TreeSet<Order> asks;
    private TreeSet<Order> bids;

}
