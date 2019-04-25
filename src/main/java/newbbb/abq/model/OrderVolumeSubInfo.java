package newbbb.abq.model;

import newbbb.enums.OrderTypeEnum;

import java.math.BigDecimal;

public class OrderVolumeSubInfo {

    private String uid;

    private BigDecimal volume;

    private OrderTypeEnum orderTypeEnum;

    public OrderVolumeSubInfo() {
    }

    public OrderVolumeSubInfo(String uid, BigDecimal volume, OrderTypeEnum orderTypeEnum) {
        this.uid = uid;
        this.volume = volume;
        this.orderTypeEnum = orderTypeEnum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
                "uid='" + uid + '\'' +
                ", volume=" + volume +
                ", orderTypeEnum=" + orderTypeEnum +
                '}';
    }
}
