package newbbb.model;

public class TxPair {
    private Integer id;

    private String pairName;

    private Integer fCoinId;

    private Integer aCoinId;

    private Long createTime;

    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPairName() {
        return pairName;
    }

    public void setPairName(String pairName) {
        this.pairName = pairName == null ? null : pairName.trim();
    }

    public Integer getfCoinId() {
        return fCoinId;
    }

    public void setfCoinId(Integer fCoinId) {
        this.fCoinId = fCoinId;
    }

    public Integer getaCoinId() {
        return aCoinId;
    }

    public void setaCoinId(Integer aCoinId) {
        this.aCoinId = aCoinId;
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