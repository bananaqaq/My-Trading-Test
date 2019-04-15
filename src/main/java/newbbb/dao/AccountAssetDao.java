package newbbb.dao;

import newbbb.model.AccountAsset;

public interface AccountAssetDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountAsset record);

    int insertSelective(AccountAsset record);

    AccountAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountAsset record);

    int updateByPrimaryKey(AccountAsset record);
}