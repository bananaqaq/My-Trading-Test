package newbbb.service.impl;

import io.netty.util.internal.StringUtil;
import newbbb.dao.BuyOrderDao;
import newbbb.enums.OrderStatusEnum;
import newbbb.enums.OrderTypeEnum;
import newbbb.model.BuyOrder;
import newbbb.service.IBuyOrderService;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyOrderServiceImpl implements IBuyOrderService {

    @Autowired
    private BuyOrderDao boDao;

    @Override
    public BuyOrder add(BuyOrder bo) {
        if(bo != null){
            long timeNow = new Date().getTime();
            bo.setUid(UUIdUtil.getOrderUUID(timeNow, OrderTypeEnum.BUY));
            bo.setStatus(OrderStatusEnum.NO_DEAL.value());
            bo.setInitialVolume(bo.getVolume());
            bo.setCreateTime(timeNow);
            bo.setUpdateTime(timeNow);
            boDao.insertSelective(bo);
            return bo;
        }
        return null;
    }

    @Override
    public int subVolume(String uid, BigDecimal volume) {
        if(!StringUtil.isNullOrEmpty(uid) || volume != null){
            Map<String, Object> param = new HashMap<>();
            param.put("uid", uid);
            param.put("volume", volume);
            param.put("updateTime", new Date().getTime());
            boDao.subVolume(param);
        }
        return 0;
    }

    @Override
    public List<BuyOrder> getUncompletedList(int txPairId, int limitNum) {
        if(limitNum > 0){
            Map<String, Object> param = new HashMap<>();
            param.put("txPairId", txPairId);
            param.put("limitNum", limitNum);
            return boDao.selectUncompletedList(param);
        }
        return null;
    }
}
