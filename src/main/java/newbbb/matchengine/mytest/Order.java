package newbbb.matchengine.mytest;

import newbbb.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private BigDecimal price;

    private BigDecimal volume;      // 当前剩余量

    private BigDecimal initialVolume;

    private String userId;

    private OrderStatusEnum status;

    private Long createTime;

    private Long updateTime;







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

    public String getUserId() {
        return userId;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setInitialVolume(BigDecimal initialVolume) {
        this.initialVolume = initialVolume;
    }







    public void subVolume(BigDecimal volume) throws Exception {
        if (this.volume.subtract(volume).compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("order_sub_volume volume invalid");
        } else {
            this.volume = this.volume.subtract(volume);
            this.updateTime = new Date().getTime();
        }
    }

    public BigDecimal getDealVolume(){
        return initialVolume.subtract(volume);
    }

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", volume=" + volume +
                ", initialVolume=" + initialVolume +
                ", userId='" + userId + '\'' +
                ", createTime=" + new Date(createTime) +
                ", updateTime=" + new Date(updateTime) +
                '}';
    }
}
