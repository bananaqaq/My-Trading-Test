package newbbb.service;


import newbbb.model.Account;

public interface IAccountService {
    int register(Account account);

    Account getByUid(String uid);

    int updateByUid(Account account);
}
