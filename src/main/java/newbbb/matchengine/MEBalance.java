package newbbb.matchengine;

import newbbb.matchengine.enums.BalanceTypeEnum;
import newbbb.model.me.Asset;
import newbbb.model.me.BalanceKey;
import newbbb.model.me.BalanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Order(2)
public class MEBalance {

    private static HashMap<String, Asset> assets;

    private static HashMap<BalanceKey, BigDecimal> balances;

    @Autowired
    private MEConfig config;

    @PostConstruct
    private void InitBalance() {
        balances = new HashMap<>();
        assets = new HashMap<>();
        for (Asset asset : config.settings().getAssets()) {
            assets.put(asset.getName(), asset);
        }
    }

    public boolean AssetExist(String asset) {
        return assets.get(asset) == null ? false : true;
    }

    public int AssetPrec(String asset) {
        Asset a = assets.get(asset);
        return assets.get(asset) == null ? -1 : a.getPrecSave();
    }

    public int AssetPrecShow(String asset) {
        Asset a = assets.get(asset);
        return assets.get(asset) == null ? -1 : a.getPrecShow();
    }


    public BigDecimal BalanceGet(BalanceKey key) {
        return balances.get(key);
    }

//    public void BalanceDel(BalanceKey key){
//        balances.remove(key);
//    }

    public BigDecimal BalanceSet(BalanceKey key, BigDecimal amt) {
        Asset a = assets.get(key.getAsset());
        if (a == null) {
            return null;
        }

        int ret = amt.compareTo(BigDecimal.ZERO);
        if (ret < 0) {
            return null;
        } else if (ret == 0) {
            balances.remove(key);
            return BigDecimal.ZERO;
        }

        amt.setScale(a.getPrecSave(), RoundingMode.DOWN);
        balances.put(key, amt);
        return amt;
    }

    public BigDecimal BalanceAdd(BalanceKey key, BigDecimal amt) {
        Asset a = assets.get(key.getAsset());
        if (a == null) {
            return null;
        }

        if (amt.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        BigDecimal result = balances.get(key);
        if (result != null) {
            result = result.add(amt).setScale(a.getPrecSave(), RoundingMode.DOWN);
            balances.replace(key, result);
            return result;
        }

        amt.setScale(a.getPrecSave(), RoundingMode.DOWN);
        balances.put(key, amt.setScale(a.getPrecSave(), RoundingMode.DOWN));
        return amt;
    }

    public BigDecimal BalanceSub(BalanceKey key, BigDecimal amt) {
        Asset a = assets.get(key);
        if (a == null) {
            return null;
        }

        if (amt.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        BigDecimal result = balances.get(key);
        if (result == null) {
            return null;
        }
        if (result.compareTo(amt) < 0) {
            return null;
        }

        result = result.subtract(amt).setScale(a.getPrecSave(), RoundingMode.DOWN);
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            balances.remove(key);
            return BigDecimal.ZERO;
        }

        balances.replace(key, result);
        return result;
    }

    public BigDecimal BalanceFreeze(Long accountId, String asset, BigDecimal amt) {
        Asset a = assets.get(asset);
        if (a == null) {
            return null;
        }

        if (amt.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }
        BalanceKey availableKey = new BalanceKey(accountId, BalanceTypeEnum.AVAILABLE, asset);
        BalanceKey freezeKey = new BalanceKey(accountId, BalanceTypeEnum.FREEZE, asset);
        BigDecimal available = balances.get(availableKey);
        if (available == null) {
            return null;
        }

        if (available.compareTo(amt) < 0) {
            return null;
        }

        if (BalanceAdd(freezeKey, amt) == null) {
            return null;
        }
        if (BalanceSub(availableKey, amt) == null) {
            return null;
        }

        return available.subtract(amt).setScale(a.getPrecSave(), RoundingMode.DOWN);
    }

    public BigDecimal BalanceUnfreeze(Long accountId, String asset, BigDecimal amt) {
        Asset a = assets.get(asset);
        if (a == null) {
            return null;
        }

        if (amt.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }
        BalanceKey availableKey = new BalanceKey(accountId, BalanceTypeEnum.AVAILABLE, asset);
        BalanceKey freezeKey = new BalanceKey(accountId, BalanceTypeEnum.FREEZE, asset);
        BigDecimal freeze = balances.get(availableKey);
        if (freeze == null) {
            return null;
        }
        if (freeze.compareTo(amt) < 0) {
            return null;
        }

        if (BalanceAdd(freezeKey, amt) == null) {
            return null;
        }
        if (BalanceSub(availableKey, amt) == null) {
            return null;
        }

        return freeze.subtract(amt).setScale(a.getPrecSave(), RoundingMode.DOWN);
    }

    public BigDecimal BalanceTotal(Long accountId, String asset) {
        BigDecimal balance = BigDecimal.ZERO;

        BalanceKey availableKey = new BalanceKey(accountId, BalanceTypeEnum.AVAILABLE, asset);
        BigDecimal available = balances.get(availableKey);
        if (available != null) {
            balance.add(available);
        }

        BalanceKey freezeKey = new BalanceKey(accountId, BalanceTypeEnum.FREEZE, asset);
        BigDecimal freeze = balances.get(freezeKey);
        if (freeze != null) {
            balance.add(freeze);
        }

        return balance;
    }

    public BalanceStatus BalanceCurrentStatus(String asset) {
        BalanceStatus bs = new BalanceStatus();
        Set<Map.Entry<BalanceKey, BigDecimal>> entrySet = balances.entrySet();
        for (Map.Entry<BalanceKey, BigDecimal> entry : entrySet) {
            BalanceKey key = entry.getKey();
            BigDecimal val = entry.getValue();
            if (!key.getAsset().equals(asset)) {
                continue;
            }
            bs.total = bs.total.add(val);
            if (key.getType() == BalanceTypeEnum.AVAILABLE) {
                bs.availableCount++;
                bs.available.add(val);
            } else {
                bs.freezeCount++;
                bs.freeze.add(val);
            }
        }

        return bs;
    }
}
