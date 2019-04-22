package newbbb.abq.model;

import java.math.BigDecimal;

public class TxAddInfo {

    private String buyerUid;

    private String sellerUid;

    private BigDecimal dealPrice;

    private BigDecimal volume;

    public TxAddInfo() {
    }

    public TxAddInfo(String buyerUid, String sellerUid, BigDecimal dealPrice, BigDecimal volume) {
        this.buyerUid = buyerUid;
        this.sellerUid = sellerUid;
        this.dealPrice = dealPrice;
        this.volume = volume;
    }

    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "TxAddInfo{" +
                "buyerUid='" + buyerUid + '\'' +
                ", sellerUid='" + sellerUid + '\'' +
                ", dealPrice=" + dealPrice +
                ", volume=" + volume +
                '}';
    }
}
