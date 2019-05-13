package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import newbbb.matchengine.enums.BalanceTypeEnum;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BalanceKey {

    private Long accountId;
    private BalanceTypeEnum type;
    private String asset;

    public BalanceKey() {
    }

    public BalanceKey(Long accountId, BalanceTypeEnum type, String asset) {
        this.accountId = accountId;
        this.type = type;
        this.asset = asset;
    }
}
