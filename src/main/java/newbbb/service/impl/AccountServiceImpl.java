package newbbb.service.impl;

import io.netty.util.internal.StringUtil;
import newbbb.constant.NBGlobalConfig;
import newbbb.dao.AccountAssetDao;
import newbbb.dao.AccountDao;
import newbbb.model.Account;
import newbbb.model.AccountAsset;
import newbbb.model.Coin;
import newbbb.service.IAccountService;
import newbbb.util.UUIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountDao aDao;

    @Autowired
    private AccountAssetDao aaDao;

    @Override
    public String register(Account account) {
        String uid = UUIdUtil.getUUID();
        long timeNow = new Date().getTime();
        account.setUid(uid);
        account.setCreateTime(timeNow);
        account.setUpdateTime(timeNow);
        int aResult = aDao.insertSelective(account);

        for (Coin coin : NBGlobalConfig.COINS) {
            if (coin != null) {
                AccountAsset aa = new AccountAsset();
                aa.setAccountUid(uid);
                aa.setCoinId(coin.getId());
//                aa.setAmt(BigDecimal.ZERO);
                aa.setAmt(new BigDecimal("1000"));
                aa.setForzenAmt(BigDecimal.ZERO);
                aa.setCreateTime(timeNow);
                aa.setUpdateTime(timeNow);
                int aaResult = aaDao.insertSelective(aa);
            }
        }

        return uid;
    }

    @Override
    public Account getByUid(String uid) {
        if(StringUtil.isNullOrEmpty(uid)){
            return null;
        }
        return aDao.selectByUid(uid);
    }

    @Override
    public int updateByUid(Account account) {
        return aDao.updateByUidSelective(account);
    }

}
