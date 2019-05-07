package newbbb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BalanceHistory {

    private Long id;
    private Long time;
    private Long accountId;
    private String asset;
    private String business;
    private BigDecimal change;
    private BigDecimal balance;
    private String detail;

}