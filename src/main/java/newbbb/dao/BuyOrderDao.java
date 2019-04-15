package newbbb.dao;

import newbbb.model.BuyOrder;

import java.math.BigDecimal;
import java.util.Map;

public interface BuyOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyOrder record);

    int insertSelective(BuyOrder record);

    BuyOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyOrder record);

    int updateByPrimaryKey(BuyOrder record);




    int subVolume(Map<String, Object> param);
}