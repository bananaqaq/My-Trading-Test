package newbbb.dao;

import newbbb.model.AccountAsset;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface AccountAssetDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountAsset record);

    int insertSelective(AccountAsset record);

    AccountAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountAsset record);

    int updateByPrimaryKey(AccountAsset record);







    AccountAsset selectByAUidAndCId(@Param("accountUid") String accountUid, @Param("coinId") Integer coinId);

    int addAmt(Map<String, Object> param);

    int subAmt(Map<String, Object> param);

    int addForzenAmt(Map<String, Object> param);

    int subForzenAmt(Map<String, Object> param);

    int forzenAsset(Map<String, Object> param);
}