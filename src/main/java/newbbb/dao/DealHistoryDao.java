package newbbb.dao;

import newbbb.model.DealHistory;

public interface DealHistoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(DealHistory record);

    int insertSelective(DealHistory record);

    DealHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DealHistory record);

    int updateByPrimaryKey(DealHistory record);
}