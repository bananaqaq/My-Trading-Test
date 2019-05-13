package newbbb.matchengine;

import newbbb.matchengine.enums.OrderSideEnum;
import newbbb.matchengine.enums.OrderTypeEnum;
import newbbb.model.me.Market;
import newbbb.model.me.Order;

public class MEMarket {

    private int orderIdStart;
    private int dealIdStart;

    private int orderMatchCompare(Order o1, Order o2){
        if(o1.getId() == o2.getId()){
            return 0;
        }
        if(o1.getType() != o2.getType()){
            return 1;
        }
        int cmp;
        if(o1.getSide() == OrderSideEnum.ASK){
            cmp = o1.getPrice().compareTo(o2.getPrice());
        }else{
            cmp = o2.getPrice().compareTo(o1.getPrice());
        }
        return cmp;
    }

    public Market MarketCreate(Market market){

        return null;
    }


    public int OrderPut(String marketName, Order order){
        if(order.getType() == OrderTypeEnum.LIMIT){
            return -1;
        }
        return 0;
    }

    public int OrderFinish(){

        return 0;
    }




}
