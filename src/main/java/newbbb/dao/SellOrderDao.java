package newbbb.dao;

import newbbb.model.SellOrder;

import java.util.List;
import java.util.Map;

public interface SellOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SellOrder record);

    int insertSelective(SellOrder record);

    SellOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SellOrder record);

    int updateByPrimaryKey(SellOrder record);



    SellOrder selectByUid(String uid);

    int subVolume(Map<String, Object> param);

    List<SellOrder> selectUncompletedList(Map<String, Object> param);
}