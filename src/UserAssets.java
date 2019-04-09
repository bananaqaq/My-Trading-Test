import java.math.BigDecimal;

public class UserAssets {

    private String userId;

    private BigDecimal usdtAmt;

    private BigDecimal btcAmt;

    public UserAssets(String userId, BigDecimal usdtAmt, BigDecimal btcAmt){
        this.userId = userId;
        this.usdtAmt = usdtAmt;
        this.btcAmt = btcAmt;
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
}
