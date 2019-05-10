package newbbb.matchengine;

import newbbb.model.me.Asset;
import newbbb.model.me.BalanceKey;

import java.math.BigDecimal;
import java.util.HashMap;

public class MEBalance {

    private HashMap<String, Asset> assets;

    private HashMap<BalanceKey, BigDecimal> balances;

    public int InitBalance(){

        return 0;
    }





    public BigDecimal BalanceGet(BalanceKey key){

        return null;
    }

    public void BalanceDel(BalanceKey key){


    }

    public BigDecimal BalanceSet(BalanceKey key, BigDecimal amt){
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
