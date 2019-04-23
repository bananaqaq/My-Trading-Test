package newbbb.abq.model;

import newbbb.enums.OrderTypeEnum;

import java.math.BigDecimal;

public class OrderVolumeSubInfo {

    private String accountUid;

    private BigDecimal volume;

    private OrderTypeEnum orderTypeEnum;

    public OrderVolumeSubInfo(String accountUid, BigDecimal volume, OrderTypeEnum orderTypeEnum) {
        this.accountUid = accountUid;
        this.volume = volume;
        this.orderTypeEnum = orderTypeEnum;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(String accountUid) {
        this.accountUid = accountUid;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public OrderTypeEnum getOrderTypeEnum() {
        return orderTypeEnum;
    }

    public void setOrderTypeEnum(OrderTypeEnum orderTypeEnum) {
        this.orderTypeEnum = orderTypeEnum;
    }

    @Override
    public String toString() {
        return "OrderVolumeSubInfo{" +
                "accountUid='" + accountUid + '\'' +
                ", volume=" + volume +
                ", orderTypeEnum=" + orderTypeEnum +
                '}';
    }
}
