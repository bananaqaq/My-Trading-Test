package newbbb.dao;

import newbbb.model.OrderHistory;

public interface OrderHistoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderHistory record);

    int insertSelective(OrderHistory record);

    OrderHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderHistory record);

    int updateByPrimaryKey(OrderHistory record);
}