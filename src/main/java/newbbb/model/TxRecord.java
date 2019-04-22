package newbbb.model;

import java.math.BigDecimal;

public class TxRecord {
    private Integer id;

    private String uid;

    private String buyerUid;

    private String sellerUid;

    private BigDecimal dealPrice;

    private BigDecimal volume;

    private BigDecimal txFee;

    private Long createTime;

    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid == null ? null : buyerUid.trim();
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid == null ? null : sellerUid.trim();
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TxRecord{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", buyerUid='" + buyerUid + '\'' +
                ", sellerUid='" + sellerUid + '\'' +
                ", dealPrice=" + dealPrice +
                ", volume=" + volume +
                ", txFee=" + txFee +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}