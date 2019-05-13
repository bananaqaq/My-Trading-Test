package newbbb.model.me;

import java.math.BigDecimal;

public class BalanceStatus {
    public BigDecimal total = BigDecimal.ZERO;
    public Long availableCount = 0L;
    public BigDecimal available = BigDecimal.ZERO;
    public Long freezeCount = 0L;
    public BigDecimal freeze = BigDecimal.ZERO;
}
