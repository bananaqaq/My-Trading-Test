package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Market {

    private String name;
    private String stock;
    private String money;
    private Integer stockPrec;
    private Integer moneyPrec;
    private Integer feePrec = 4;
    private BigDecimal minAmount = new BigDecimal("0.01");

}
