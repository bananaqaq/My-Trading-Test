package newbbb.service.impl;

import newbbb.dao.TxRecordDao;
import newbbb.model.TxRecord;
import newbbb.service.ITxRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxRecordServiceImpl implements ITxRecordService {

    @Autowired
    private TxRecordDao trDao;

    @Override
    public int add(TxRecord txRecord) {
        if(txRecord != null){
            return trDao.insert(txRecord);
        }
        return 0;
    }
}
