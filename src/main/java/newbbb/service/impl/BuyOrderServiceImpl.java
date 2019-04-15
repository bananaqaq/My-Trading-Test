package newbbb.service.impl;

import io.netty.util.internal.StringUtil;
import newbbb.dao.BuyOrderDao;
import newbbb.enums.OrderStatusEnum;
import newbbb.model.BuyOrder;
import newbbb.service.IBuyOrderService;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BuyOrderServiceImpl implements IBuyOrderService {

    @Autowired
    private BuyOrderDao boDao;

    @Override
    public int add(BuyOrder bo) {
        if(bo != null){
            long timeNow = new Date().getTime();
            bo.setUid(UUIdUtil.getUUID());
            bo.setStatus(OrderStatusEnum.NO_DEAL.value());
            bo.setInitialVolume(bo.getVolume());
            bo.setCreateTime(timeNow);
            bo.setUpdateTime(timeNow);
            return boDao.insertSelective(bo);
        }
        return 0;
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


}
