package newbbb.service;

import newbbb.model.TxRecord;
import java.util.ArrayList;

public interface ITxRecordService {

    String add(TxRecord txRecord);

    int addList(ArrayList<TxRecord> taiList);

}
