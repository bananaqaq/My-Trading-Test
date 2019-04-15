package newbbb.service.impl;

import newbbb.dao.TxPairDao;
import newbbb.model.TxPair;
import newbbb.service.ITxPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ITxPairServiceImpl implements ITxPairService {

    @Autowired
    private TxPairDao tpDao;

    @Override
    public List<TxPair> getList() {
        return tpDao.selectAll();
    }

    @Override
    public int add(TxPair txPair) {
        if (txPair != null) {
            return tpDao.insertSelective(txPair);
        }
        return 0;
    }
}
