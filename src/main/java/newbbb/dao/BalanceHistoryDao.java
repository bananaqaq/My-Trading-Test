package newbbb.dao;

import newbbb.model.BalanceHistory;

public interface BalanceHistoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(BalanceHistory record);

    int insertSelective(BalanceHistory record);

    BalanceHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BalanceHistory record);

    int updateByPrimaryKeyWithBLOBs(BalanceHistory record);

    int updateByPrimaryKey(BalanceHistory record);
}