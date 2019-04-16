package newbbb.service.impl;

import io.netty.util.internal.StringUtil;
import newbbb.dao.SellOrderDao;
import newbbb.enums.OrderStatusEnum;
import newbbb.model.SellOrder;
import newbbb.service.ISellOrderService;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellOrderServiceImpl implements ISellOrderService {

    @Autowired
    private SellOrderDao soDao;

    @Override
    public int add(SellOrder so) {
        if(soDao!=null){
            long timeNow = new Date().getTime();
            so.setUid(UUIdUtil.getUUID());
            so.setStatus(OrderStatusEnum.NO_DEAL.value());
            so.setInitialVolume(so.getVolume());
            so.setCreateTime(timeNow);
            so.setUpdateTime(timeNow);
            return soDao.insertSelective(so);
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
            soDao.subVolume(param);
        }
        return 0;
    }

    @Override
    public List<SellOrder> getUncompletedList(int txPairId, int limitNum) {
        if(limitNum > 0){
            Map<String, Object> param = new HashMap<>();
            param.put("txPairId", txPairId);
            param.put("limitNum", limitNum);
            return soDao.selectUncompletedList(param);
        }
        return null;
    }
}
