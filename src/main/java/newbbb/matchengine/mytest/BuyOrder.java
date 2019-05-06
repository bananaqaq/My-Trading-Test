package newbbb.matchengine.mytest;

import newbbb.matchengine.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

public class BuyOrder extends Order {

    public BuyOrder(String userId, String price, String volume){
        this.setUserId(userId);
        this.setPrice(new BigDecimal(price));
        this.setVolume(new BigDecimal(volume));
        this.setInitialVolume(new BigDecimal(volume));
        this.setStatus(OrderStatusEnum.NO_DEAL);
        long createTime = new Date().getTime();
        this.setCreateTime(createTime);
        this.setUpdateTime(createTime);
    }

}
