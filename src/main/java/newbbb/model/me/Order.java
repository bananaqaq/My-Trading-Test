package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import newbbb.matchengine.enums.OrderSideEnum;
import newbbb.matchengine.enums.OrderTypeEnum;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Order {

    private Long id;
    private OrderTypeEnum type;
    private OrderSideEnum side;
    private Long createTime;
    private Long updateTime;
    private Long accountId;
    private String market;
    private String source;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal takerFee;
    private BigDecimal makerFee;
    private BigDecimal left;
    private BigDecimal freeze;
    private BigDecimal dealStock;
    private BigDecimal dealMoney;
    private BigDecimal dealFee;

}
