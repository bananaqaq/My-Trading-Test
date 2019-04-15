package newbbb.dao;

import newbbb.model.Coin;

import java.util.List;

public interface CoinDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Coin record);

    int insertSelective(Coin record);

    Coin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coin record);

    int updateByPrimaryKey(Coin record);



    List<Coin> selectAll();
}