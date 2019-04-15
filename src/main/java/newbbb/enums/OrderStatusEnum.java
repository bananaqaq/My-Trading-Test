package newbbb.enums;

import java.security.PublicKey;

public enum OrderStatusEnum {
    NO_DEAL("未成交", (short) 1),
    PARTIAL_DEAL("部分成交", (short) 1),
    COMPLETED("已完成", (short) 2),
    REVOKED("撤销", (short) 3),
    ROLLBACK ("回滚", (short) 4);

    private String status;

    private short value;

    OrderStatusEnum(String status, short value){
        this.status = status;
        this.value = value;
    }

    public String status(){
        return this.status;
    }

    public short value(){
        return this.value;
    }
}
