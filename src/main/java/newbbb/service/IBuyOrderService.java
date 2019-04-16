package newbbb.service;

import newbbb.model.BuyOrder;
import java.math.BigDecimal;
import java.util.List;

public interface IBuyOrderService {

    int add(BuyOrder bo);

    int subVolume(String uid, BigDecimal volume);

    List<BuyOrder> getUncompletedList(int txPairId, int limitNum);

}
