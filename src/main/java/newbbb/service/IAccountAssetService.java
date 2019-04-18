package newbbb.service;

import newbbb.enums.AssetUpdateEnum;
import newbbb.model.AccountAsset;
import java.math.BigDecimal;

public interface IAccountAssetService {

    AccountAsset getByAUidAndCId(String accountUid, Integer coinId);

    int updateAmt(String accountUid, Integer coinId, BigDecimal amt, AssetUpdateEnum assetUpdateEnum);

    int updateForzenAmt(String accountUid, Integer coinId, BigDecimal amt, AssetUpdateEnum assetUpdateEnum);

    int forzenAsset(String accountUid, Integer coinId, BigDecimal amt);

}
