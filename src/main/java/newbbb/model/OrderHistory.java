package newbbb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderHistory {

    private Long id;
    private Long createTime;
    private Long finishTime;
    private Long accountId;
    private String market;
    private String source;
    private Byte t;
    private Byte side;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal takerFee;
    private BigDecimal makerFee;
    private BigDecimal dealStock;
    private BigDecimal dealMoney;
    private BigDecimal dealFee;

}