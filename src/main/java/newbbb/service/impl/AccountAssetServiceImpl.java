package newbbb.service.impl;

import io.netty.util.internal.StringUtil;
import newbbb.dao.AccountAssetDao;
import newbbb.enums.AssetUpdateEnum;
import newbbb.model.AccountAsset;
import newbbb.service.IAccountAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountAssetServiceImpl implements IAccountAssetService {

    @Autowired
    private AccountAssetDao aaDao;

    @Override
    public AccountAsset getByAUidAndCId(String accountUid, Integer coinId) {
        if(StringUtil.isNullOrEmpty(accountUid) || coinId == null){
            return null;
        }
        return aaDao.selectByAUidAndCId(accountUid, coinId);
    }

    @Override
    public int updateAmt(String accountUid, Integer coinId, BigDecimal amt, AssetUpdateEnum assetUpdateEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountUid", accountUid);
        map.put("coinId", coinId);
        map.put("amt", amt);
        switch (assetUpdateEnum){
            case ADD:
                return aaDao.addAmt(map);
            case SUB:
                return aaDao.subAmt(map);
            default:
                return 0;
        }
    }

    @Override
    public int updateForzenAmt(String accountUid, Integer coinId, BigDecimal amt, AssetUpdateEnum assetUpdateEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountUid", accountUid);
        map.put("coinId", coinId);
        map.put("amt", amt);
        switch (assetUpdateEnum){
            case ADD:
                return aaDao.addForzenAmt(map);
            case SUB:
                return aaDao.subForzenAmt(map);
            default:
                return 0;
        }
    }

    @Override
    public int forzenAsset(String accountUid, Integer coinId, BigDecimal amt) {
        Map<String, Object> param = new HashMap<>();
        param.put("accountUid", accountUid);
        param.put("coinId", coinId);
        param.put("amt", amt);
        return aaDao.forzenAsset(param);
    }

}
