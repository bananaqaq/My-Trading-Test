package newbbb.service;

import newbbb.model.Account;

public interface IAccountService {
    String register(Account account);

    Account getByUid(String uid);

    int updateByUid(Account account);

    String[] getAllAccount(int limitNum);
}
