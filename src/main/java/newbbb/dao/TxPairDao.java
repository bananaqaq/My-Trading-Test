package newbbb.dao;

import newbbb.model.TxPair;

import java.util.List;

public interface TxPairDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TxPair record);

    int insertSelective(TxPair record);

    TxPair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TxPair record);

    int updateByPrimaryKey(TxPair record);






    List<TxPair> selectAll();


}