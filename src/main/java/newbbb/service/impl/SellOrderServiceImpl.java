package newbbb.service.impl;

import newbbb.dao.SellOrderDao;
import newbbb.model.SellOrder;
import newbbb.service.ISellOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellOrderServiceImpl implements ISellOrderService {

    @Autowired
    private SellOrderDao soDao;

    @Override
    public int add(SellOrder so) {
        if(soDao!=null){
            return soDao.insertSelective(so);
        }
        return 0;
    }
}
