package newbbb.model;

import java.math.BigDecimal;

public class BuyOrder extends Order {

    public BuyOrder(){super();}

    public BuyOrder(int txPairId, String accountUid, String price, String volume){
        super();
        this.setTxPairId(txPairId);
        this.setAccountUid(accountUid);
        this.setPrice(new BigDecimal(price));
        this.setVolume(new BigDecimal(volume));
    }

}