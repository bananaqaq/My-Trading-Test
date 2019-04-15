package newbbb.service;

import newbbb.model.BuyOrder;
import java.math.BigDecimal;

public interface IBuyOrderService {

    int add(BuyOrder bo);

    int subVolume(String uid, BigDecimal volume);

}
