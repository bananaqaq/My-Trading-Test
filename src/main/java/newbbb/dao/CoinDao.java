package newbbb.dao;

import newbbb.model.Coin;

public interface CoinDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Coin record);

    int insertSelective(Coin record);

    Coin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coin record);

    int updateByPrimaryKey(Coin record);
}