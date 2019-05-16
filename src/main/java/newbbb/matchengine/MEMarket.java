package newbbb.matchengine;

import com.alibaba.fastjson.JSONObject;
import newbbb.matchengine.enums.OrderSideEnum;
import newbbb.matchengine.enums.OrderTypeEnum;
import newbbb.model.me.Market;
import newbbb.model.me.Order;
import newbbb.model.me.TradingMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
@org.springframework.core.annotation.Order(7)
public class MEMarket {

    private long orderIdStart;
    private long dealIdStart;

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
        if (cmp != 0) {
            return cmp;
        }
        return o1.getId() > o2.getId() ? 1 : -1;
    };

    Comparator<Order> orderIdCompare = (Order o1, Order o2) -> {
        long id1 = o1.getId();
        long id2 = o2.getId();
        if (id1 == id2) {
            return 0;
        }
        return id1 > id2 ? -1 : 1;
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

    public int GetStatus(){
        return 0;
    }

    public int PutLimitOrder(TradingMarket tm, long askCount, BigDecimal askAmt, long bidCount, BigDecimal bidAmt){

        return 0;
    }

    public int PutMarketOrder(){
        return 0;
    }

    public int CancelOrder(){
        return 0;
    }

    public int PutOrder(){
        return 0;
    }

    public JSONObject GetOrderInfo(){
        return null;
    }

    public Order GetOrder(){
        return null;
    }

    public ConcurrentSkipListSet<Order> GetOrderList(){
        return null;
    }

    public void MarketStatus(){

    }




    private int orderPut(TradingMarket tm, Order order) {
        if (order.getType() == OrderTypeEnum.LIMIT) {
            return -1;
        }

        String stock = tm.getStock();
        String money = tm.getMoney();
        HashMap<Long, ConcurrentSkipListSet<Order>> accounts = tm.getAccounts();
        HashMap<Long, Order> orders = tm.getOrders();
        ConcurrentSkipListSet<Order> asks = tm.getAsks();
        ConcurrentSkipListSet<Order> bids = tm.getBids();
        BigDecimal price = order.getPrice();
        BigDecimal left = order.getLeft();
        long orderId = order.getId();
        long accountId = order.getAccountId();

        if(orders.containsKey(orderId)){
            return -2;
        }
        orders.put(orderId, order);

        ConcurrentSkipListSet<Order> orderList = accounts.get(accountId);
        if (orderList != null && orderList.size() != 0) {
            if (!orderList.add(order)) {
                return -3;
            }
        } else {
            ConcurrentSkipListSet<Order> newOrderList = new ConcurrentSkipListSet<>(orderIdCompare);
            newOrderList.add(order);
            accounts.put(accountId, newOrderList);
        }

        if (order.getSide() == OrderSideEnum.ASK) {
            if (!asks.add(order)) {
                return -4;
            }
            order.setFreeze(left);
            if (meBalance.BalanceFreeze(accountId, stock, left) == null) {
                return -5;
            }
        } else {
            if (!bids.add(order)) {
                return -6;
            }
            BigDecimal result = price.multiply(left);
            order.setFreeze(result);
            if (meBalance.BalanceFreeze(accountId, money, result) == null) {
                return -7;
            }
        }

        return 0;
    }

    private int orderFinish(boolean real, TradingMarket tm, Order order) {
        String money = tm.getMoney();
        String stock = tm.getStock();
        ConcurrentSkipListSet<Order> asks = tm.getAsks();
        ConcurrentSkipListSet<Order> bids = tm.getBids();
        long accountId = order.getAccountId();
        BigDecimal freeze = order.getFreeze();

        if (order.getSide() == OrderSideEnum.ASK) {
            if (asks.contains(order)) {
                asks.remove(order);
            }
            if (freeze.compareTo(BigDecimal.ZERO) > 0) {
                if (meBalance.BalanceUnfreeze(accountId, stock, freeze) == null) {
                    return -1;
                }
            }
        } else {
            if (bids.contains(order)) {
                bids.remove(order);
            }
            if (freeze.compareTo(BigDecimal.ZERO) > 0) {
                if (meBalance.BalanceUnfreeze(accountId, money, freeze) == null) {
                    return -2;
                }
            }
        }

        return 0;
    }

    private int appendBalanceTradeAdd(Order order, String asset, BigDecimal change, BigDecimal price, BigDecimal amt){

        return 0;
    }

    private int appendBalanceTradeSub(Order order, String asset, BigDecimal change, BigDecimal price, BigDecimal amt){
        return 0;
    }

    private int appendBalanceFee(Order order, String asset, BigDecimal change, BigDecimal price, BigDecimal amt, BigDecimal feeRate){
        return 0;
    }

    private int executeLimitAskOrder(boolean real, TradingMarket tm, Order taker) {
        BigDecimal price;
        BigDecimal amt;
        BigDecimal deal;
        BigDecimal askFee;
        BigDecimal bidFee;
        BigDecimal result;

        Iterator<Order> iterator = tm.getBids().iterator();
        while (iterator.hasNext()) {
            if (taker.getLeft().compareTo(BigDecimal.ZERO) == 0) {
                break;
            }

            Order maker = iterator.next();
            if (taker.getPrice().compareTo(maker.getPrice()) > 0) {
                break;
            }

            price = maker.getPrice();
            if (taker.getLeft().compareTo(maker.getLeft()) < 0) {
                amt = taker.getLeft();
            } else {
                amt = maker.getLeft();
            }

            deal = price.multiply(amt);
            askFee = deal.multiply(taker.getTakerFee());
            bidFee = deal.multiply(maker.getMakerFee());

            Long timeNow = new Date().getTime();
            taker.setUpdateTime(timeNow);
            maker.setUpdateTime(timeNow);
            Long dealId = ++dealIdStart;
            if(real){
                
            }
        }

        return 0;
    }

    private int executeLimitBidOrder(boolean real, TradingMarket tm, Order taker){
        return 0;
    }

    private int executeMarketAskOrder(boolean real, TradingMarket tm, Order taker){
        return 0;
    }

    private int executeMarketBidOrder(boolean real, TradingMarket tm, Order taker){
        return 0;
    }
}
