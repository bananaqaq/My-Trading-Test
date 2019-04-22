package newbbb.dao;

import newbbb.model.TxRecord;

import java.util.List;

public interface TxRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TxRecord record);

    int insertSelective(TxRecord record);

    TxRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TxRecord record);

    int updateByPrimaryKey(TxRecord record);



    int insertList(List<TxRecord> list);
}