package newbbb.service.impl;

import newbbb.dao.CoinDao;
import newbbb.model.Coin;
import newbbb.service.ICoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoinServiceImpl implements ICoinService {

    @Autowired
    private CoinDao cDao;

    @Override
    public List<Coin> getList() {
        return cDao.selectAll();
    }

    @Override
    public int add(Coin coin) {
        if (coin != null) {
            return cDao.insertSelective(coin);
        }
        return 0;
    }
}
