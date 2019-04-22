package newbbb.abq.model;

import newbbb.enums.AssetTypeEnum;
import newbbb.enums.AssetUpdateEnum;
import java.math.BigDecimal;

public class AssetUpdateInfo {

    private String accountUid;

    private Integer coinId;

    private BigDecimal volume;

    private AssetUpdateEnum assetUpdateEnum;

    private AssetTypeEnum assetTypeEnum;

    public AssetUpdateInfo(){}

    public AssetUpdateInfo(String accountUid, Integer coinId, BigDecimal volume, AssetUpdateEnum assetUpdateEnum, AssetTypeEnum assetTypeEnum) {
        this.accountUid = accountUid;
        this.coinId = coinId;
        this.volume = volume;
        this.assetUpdateEnum = assetUpdateEnum;
        this.assetTypeEnum = assetTypeEnum;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(String accountUid) {
        this.accountUid = accountUid;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public AssetUpdateEnum getAssetUpdateEnum() {
        return assetUpdateEnum;
    }

    public void setAssetUpdateEnum(AssetUpdateEnum assetUpdateEnum) {
        this.assetUpdateEnum = assetUpdateEnum;
    }

    public AssetTypeEnum getAssetTypeEnum() {
        return assetTypeEnum;
    }

    public void setAssetTypeEnum(AssetTypeEnum assetTypeEnum) {
        this.assetTypeEnum = assetTypeEnum;
    }

    @Override
    public String toString() {
        return "AssetUpdateInfo{" +
                "accountUid='" + accountUid + '\'' +
                ", coinId=" + coinId +
                ", volume=" + volume +
                ", assetUpdateEnum=" + assetUpdateEnum +
                ", assetTypeEnum=" + assetTypeEnum +
                '}';
    }
}
