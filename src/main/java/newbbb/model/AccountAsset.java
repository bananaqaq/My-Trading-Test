package newbbb.model;

import java.math.BigDecimal;

public class AccountAsset {
    private Integer id;

    private Integer accountUid;

    private Integer coinId;

    private BigDecimal amt;

    private BigDecimal forzenAmt;

    private Long createTime;

    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(Integer accountUid) {
        this.accountUid = accountUid;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getForzenAmt() {
        return forzenAmt;
    }

    public void setForzenAmt(BigDecimal forzenAmt) {
        this.forzenAmt = forzenAmt;
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
}