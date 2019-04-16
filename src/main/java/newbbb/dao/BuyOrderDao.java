package newbbb.dao;

import newbbb.model.BuyOrder;

import java.util.List;
import java.util.Map;

public interface BuyOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyOrder record);

    int insertSelective(BuyOrder record);

    BuyOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyOrder record);

    int updateByPrimaryKey(BuyOrder record);





    BuyOrder selectByUid(String uid);

    int subVolume(Map<String, Object> param);

    List<BuyOrder> selectUncompletedList(Map<String, Object> param);
}