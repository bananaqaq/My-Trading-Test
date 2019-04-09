import myEnum.OrderStatusEnum;

import java.math.BigDecimal;

public class BuyOrder extends Order {

    public BuyOrder(String userId, String price, String volume, long createTime){
        this.setUserId(userId);
        this.setPrice(new BigDecimal(price));
        this.setVolume(new BigDecimal(volume));
        this.setInitialVolume(new BigDecimal(volume));
        this.setStatus(OrderStatusEnum.NO_DEAL);
        this.setCreateTime(createTime);
        this.setUpdateTime(createTime);
    }

}
