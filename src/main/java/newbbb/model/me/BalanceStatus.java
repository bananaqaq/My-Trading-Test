package newbbb.model.me;

import java.math.BigDecimal;

public class BalanceStatus {
    public BigDecimal total = BigDecimal.ZERO;
    public Integer availableCount = 0;
    public BigDecimal available = BigDecimal.ZERO;
    public Integer freezeCount = 0;
    public BigDecimal freeze = BigDecimal.ZERO;
}
