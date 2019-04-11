package newbbb.dao;

import newbbb.model.TxRecord;

public interface TxRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TxRecord record);

    int insertSelective(TxRecord record);

    TxRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TxRecord record);

    int updateByPrimaryKey(TxRecord record);
}