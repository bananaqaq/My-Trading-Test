package newbbb.service;

import newbbb.model.SellOrder;
import java.math.BigDecimal;
import java.util.List;

public interface ISellOrderService {

    int add(SellOrder so);

    int subVolume(String uid, BigDecimal volume);

    List<SellOrder> getUncompletedList(int txPairId, int limitNum);

}
