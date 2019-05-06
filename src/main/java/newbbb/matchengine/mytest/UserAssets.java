package newbbb.matchengine.mytest;


import newbbb.matchengine.enums.AssetsUpateEnum;
import java.math.BigDecimal;

public class UserAssets {

    private String userId;

    private BigDecimal usdtAmt;

    private BigDecimal btcAmt;

    private BigDecimal usdtForzenAmt;

    private BigDecimal btcForzenAmt;

    public UserAssets(String userId, String usdtAmt, String btcAmt) {
        this.userId = userId;
        this.usdtAmt = new BigDecimal(usdtAmt);
        this.btcAmt = new BigDecimal(btcAmt);
        this.usdtForzenAmt = BigDecimal.ZERO;
        this.btcForzenAmt = BigDecimal.ZERO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getUsdtAmt() {
        return usdtAmt;
    }

    public void setUsdtAmt(BigDecimal usdtAmt) {
        this.usdtAmt = usdtAmt;
    }

    public BigDecimal getBtcAmt() {
        return btcAmt;
    }

    public void setBtcAmt(BigDecimal btcAmt) {
        this.btcAmt = btcAmt;
    }

    public BigDecimal getUsdtForzenAmt() {
        return usdtForzenAmt;
    }

    public void setUsdtForzenAmt(BigDecimal usdtForzenAmt) {
        this.usdtForzenAmt = usdtForzenAmt;
    }

    public BigDecimal getBtcForzenAmt() {
        return btcForzenAmt;
    }

    public void setBtcForzenAmt(BigDecimal btcForzenAmt) {
        this.btcForzenAmt = btcForzenAmt;
    }


    public void updateUsdtAmt(BigDecimal optAmt, AssetsUpateEnum auEnum) throws Exception {
        this.usdtAmt = updateAmt(this.usdtAmt, optAmt, auEnum);
    }

    public void updateBtcAmt(BigDecimal optAmt, AssetsUpateEnum auEnum) throws Exception {
        this.btcAmt = updateAmt(this.btcAmt, optAmt, auEnum);
    }

    public void updateUsdtForzenAmt(BigDecimal optAmt, AssetsUpateEnum auEnum) throws Exception {
        this.usdtForzenAmt = updateAmt(this.usdtForzenAmt, optAmt, auEnum);
    }

    public void updateBtcForzenAmt(BigDecimal optAmt, AssetsUpateEnum auEnum) throws Exception {
        this.btcForzenAmt = updateAmt(this.btcForzenAmt, optAmt, auEnum);
    }

    private BigDecimal updateAmt(BigDecimal amt, BigDecimal optAmt, AssetsUpateEnum auEnum) throws Exception {
        switch (auEnum) {
            case ADD:
                return amt.add(optAmt);
            case SUB:
                BigDecimal result = amt.subtract(optAmt);
                if(result.compareTo(BigDecimal.ZERO) >= 0){
                    return amt.subtract(optAmt);
                }else{
                    throw new Exception("user_asset_sub_amt amt invalid");
                }
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "UserAssets{" +
                "userId='" + userId + '\'' +
                ", usdtAmt=" + usdtAmt +
                ", btcAmt=" + btcAmt +
                ", usdtForzenAmt=" + usdtForzenAmt +
                ", btcForzenAmt=" + btcForzenAmt +
                '}';
    }
}
