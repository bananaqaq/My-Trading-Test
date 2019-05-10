package newbbb.matchengine;

import newbbb.model.me.Asset;
import newbbb.model.me.BalanceKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;

@Component
@Order(5)
public class MEBalance {

    private static HashMap<String, Asset> assets;

    private static HashMap<BalanceKey, BigDecimal> balances;

    @Autowired
    private MEConfig config;

    @PostConstruct
    private void InitBalance(){
        assets = new HashMap<>();
        for(Asset asset : config.settings().getAssets()){
            assets.put(asset.getName(), asset);
        }
    }



    public boolean AssetExist(String asset) {
        return assets.get(asset) == null ? false : true;
    }

    public int AssetPrec(String asset){
        Asset a = assets.get(asset);
        return assets.get(asset) == null ? -1 : a.getPrecSave();
    }

    public int AssetPrecShow(String asset){
        Asset a = assets.get(asset);
        return assets.get(asset) == null ? -1 : a.getPrecShow();
    }




    public BigDecimal BalanceGet(BalanceKey key){
        return balances.get(key);
    }

    public void BalanceDel(BalanceKey key){
        balances.remove(key);
    }

    public BigDecimal BalanceSet(BalanceKey key, BigDecimal amt){
        Asset a = assets.get(key.getAsset());
        if(a == null){
            return null;
        }

        int ret = amt.compareTo(BigDecimal.ZERO);
        if(ret < 0){
            return null;
        }else if(ret == 0){
            balances.remove(key);
            return BigDecimal.ZERO;
        }

        return null;
    }

    public BigDecimal BalanceAdd(BalanceKey key, BigDecimal amt){
        return null;
    }

    public BigDecimal BalanceSub(BalanceKey key, BigDecimal amt){
        return null;
    }

    public BigDecimal BalanceFreeze(Long accountId, String asset, BigDecimal amt){
        return null;
    }

    public BigDecimal BalanceUnfreeze(Long accountId, String asset, BigDecimal amt){
        return null;
    }

    public BigDecimal BalanceTotal(Long accountId, String asset){
        return null;
    }

    public int BalanceStatus(){
        return 0;
    }


}
