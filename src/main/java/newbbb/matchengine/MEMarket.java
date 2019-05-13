package newbbb.matchengine;

import newbbb.matchengine.enums.OrderSideEnum;
import newbbb.matchengine.enums.OrderTypeEnum;
import newbbb.model.me.Market;
import newbbb.model.me.Order;
import newbbb.model.me.TradingMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
@org.springframework.core.annotation.Order(7)
public class MEMarket {

    private int orderIdStart;
    private int dealIdStart;

    @Autowired
    private MEBalance meBalance;



    Comparator<Order> orderMatchCompare = (Order o1, Order o2) -> {
        if (o1.getId() == o2.getId()) {
            return 0;
        }
        if (o1.getType() != o2.getType()) {
            return 1;
        }
        int cmp;
        if (o1.getSide() == OrderSideEnum.ASK) {
            cmp = o1.getPrice().compareTo(o2.getPrice());
        } else {
            cmp = o2.getPrice().compareTo(o1.getPrice());
        }
        return cmp;
    };

    public TradingMarket MarketCreate(Market conf) {
        TradingMarket tm = new TradingMarket();
        String name = conf.getName();
        String stock = conf.getStock();
        String money = conf.getMoney();
        Integer stockPrec = conf.getStockPrec();
        Integer moneyPrec = conf.getMoneyPrec();
        Integer feePrec = conf.getFeePrec();
        BigDecimal minAmount = conf.getMinAmount();

        if (!meBalance.AssetExist(money) || !meBalance.AssetExist(stock)) {
            return null;
        }
        if (stockPrec + moneyPrec > meBalance.AssetPrec(money)) {
            return null;
        }
        if (stockPrec + feePrec > meBalance.AssetPrec(stock)) {
            return null;
        }
        if (moneyPrec + feePrec > meBalance.AssetPrec(money)) {
            return null;
        }

        tm.setName(name);
        tm.setStock(stock);
        tm.setMoney(money);
        tm.setStockPrec(stockPrec);
        tm.setFeePrec(feePrec);
        tm.setMinAmount(minAmount);

        tm.setOrders(new HashMap<>());
        tm.setAccounts(new HashMap<>());
        tm.setAsks(new ConcurrentSkipListSet<>(orderMatchCompare));
        tm.setBids(new ConcurrentSkipListSet<>(orderMatchCompare));

        return tm;
    }


    public int OrderPut(String marketName, Order order) {

        if (order.getType() == OrderTypeEnum.LIMIT) {
            return -1;
        }




        return 0;
    }

    public int OrderFinish() {

        return 0;
    }


}
