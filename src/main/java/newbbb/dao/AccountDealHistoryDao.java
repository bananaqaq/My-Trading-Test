package newbbb.dao;

import newbbb.model.AccountDealHistory;

public interface AccountDealHistoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(AccountDealHistory record);

    int insertSelective(AccountDealHistory record);

    AccountDealHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountDealHistory record);

    int updateByPrimaryKey(AccountDealHistory record);
}