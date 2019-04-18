package newbbb.model;

import java.math.BigDecimal;

public class SellOrder extends Order  {

    public SellOrder(){super();}

    public SellOrder(int txPairId, String accountUid, String price, String volume){
        super();
        this.setTxPairId(txPairId);
        this.setAccountUid(accountUid);
        this.setPrice(new BigDecimal(price));
        this.setVolume(new BigDecimal(volume));
    }

}