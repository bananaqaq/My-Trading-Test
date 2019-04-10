import java.math.BigDecimal;

public class TxRecord {

    private String buyerId;

    private String sellerId;

    private BigDecimal dealPrice;

    private BigDecimal volume;

    private BigDecimal txFee;

    private Long createTime;

    public TxRecord(String buyerId, String sellerId, String dealPrice, String volume, long createTime){
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.dealPrice = new BigDecimal(dealPrice);
        this.volume = new BigDecimal(volume);
        this.txFee = BigDecimal.ZERO;
        this.createTime = createTime;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public BigDecimal getTxFee() {
        return txFee;
    }

    public void setTxFee(BigDecimal txFee) {
        this.txFee = txFee;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


}
