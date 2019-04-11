package newbbb.dao;

import newbbb.model.SellOrder;

public interface SellOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SellOrder record);

    int insertSelective(SellOrder record);

    SellOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SellOrder record);

    int updateByPrimaryKey(SellOrder record);
}