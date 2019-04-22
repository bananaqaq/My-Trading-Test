package newbbb.service.impl;

import newbbb.dao.TxRecordDao;
import newbbb.model.TxRecord;
import newbbb.service.ITxRecordService;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TxRecordServiceImpl implements ITxRecordService {

    @Autowired
    private TxRecordDao trDao;

    @Override
    public String add(TxRecord txRecord) {
        if (txRecord != null) {
            long timeNow = new Date().getTime();
            String uid = UUIdUtil.getUUID();
            txRecord.setUid(UUIdUtil.getUUID());
            txRecord.setTxFee(BigDecimal.ZERO);
            txRecord.setCreateTime(timeNow);
            txRecord.setUpdateTime(timeNow);
            if (trDao.insert(txRecord) == 1) {
                return uid;
            }
        }
        return null;
    }

    @Override
    public int addList(ArrayList<TxRecord> trList) {

        return trDao.insertList(trList);
    }
}
