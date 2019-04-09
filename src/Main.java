import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import myEnum.*;

public class Main {

    public static void main(String[] args) {

        // init
        UserAssets ua1 = new UserAssets("111", new BigDecimal("1000"), new BigDecimal("1000"));
        UserAssets ua2 = new UserAssets("222", new BigDecimal("1000"), new BigDecimal("1000"));

        SellOrder so1 = new SellOrder(ua1.getUserId(), "106", "10", 6L);
        SellOrder so2 = new SellOrder(ua1.getUserId(), "105", "10", 5L);
        SellOrder so3 = new SellOrder(ua1.getUserId(), "104", "10", 4L);
        SellOrder so4 = new SellOrder(ua1.getUserId(), "103", "10", 3L);
        SellOrder so5 = new SellOrder(ua1.getUserId(), "102", "10", 2L);
        SellOrder so6 = new SellOrder(ua1.getUserId(), "101", "10", 1L);

        BuyOrder bo1 = new BuyOrder(ua1.getUserId(), "100", "10", 1L);
        BuyOrder bo2 = new BuyOrder(ua1.getUserId(), "99", "10", 2L);
        BuyOrder bo3 = new BuyOrder(ua1.getUserId(), "98", "10", 3L);
        BuyOrder bo4 = new BuyOrder(ua1.getUserId(), "97", "10", 4L);
        BuyOrder bo5 = new BuyOrder(ua1.getUserId(), "96", "10", 5L);
        BuyOrder bo6 = new BuyOrder(ua1.getUserId(), "95", "10", 6L);

        Hashtable<String, UserAssets> uaTable = new Hashtable<>();

        uaTable.put(ua1.getUserId(), ua1);
        uaTable.put(ua2.getUserId(), ua2);

        ArrayList<SellOrder> soList = new ArrayList<>();
        ArrayList<BuyOrder> boList = new ArrayList<>();

        soList.add(so1);
        soList.add(so2);
        soList.add(so3);
        soList.add(so4);
        soList.add(so5);
        soList.add(so6);

        boList.add(bo1);
        boList.add(bo2);
        boList.add(bo3);
        boList.add(bo4);
        boList.add(bo5);
        boList.add(bo6);


        // request info
        String userId = "111";
        BigDecimal price = new BigDecimal("100");
        BigDecimal volume = new BigDecimal("100");
        TxDirectionEnum txDirectionEnum = TxDirectionEnum.BUY;


        // tx opt
        // search user
        UserAssets ua = uaTable.get(userId);


    }


}
