package newbbb.model;

import java.math.BigDecimal;

public class Order {
    private Integer id;

    private String uid;

    private Integer txPairId;

    private String accountUid;

    private BigDecimal price;

    private BigDecimal volume;

    private BigDecimal initialVolume;

    private Short status;

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

    public Integer getTxPairId() {
        return txPairId;
    }

    public void setTxPairId(Integer txPairId) {
        this.txPairId = txPairId;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(String accountUid) {
        this.accountUid = accountUid == null ? null : accountUid.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getInitialVolume() {
        return initialVolume;
    }

    public void setInitialVolume(BigDecimal initialVolume) {
        this.initialVolume = initialVolume;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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





    public void subVolume(BigDecimal volume) throws Exception {
        if (this.volume.subtract(volume).compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("order_sub_volume volume invalid");
        } else {
            this.volume = this.volume.subtract(volume);
        }
    }
}
