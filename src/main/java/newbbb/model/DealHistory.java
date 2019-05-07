package newbbb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DealHistory {

    private Long id;
    private Long time;
    private Long accountId;
    private String market;
    private Long dealId;
    private Long orderId;
    private Long dealOrderId;
    private Byte side;
    private Byte role;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal deal;
    private BigDecimal fee;
    private BigDecimal dealFee;

}