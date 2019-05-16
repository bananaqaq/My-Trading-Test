package newbbb.model;

import java.math.BigDecimal;

public class DealHistory {
    private Long id;

    private Long time;

    private Long accountId;

    private Long dealId;

    private Long orderId;

    private Long dealOrderId;

    private Byte role;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal deal;

    private BigDecimal fee;

    private BigDecimal dealFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDealOrderId() {
        return dealOrderId;
    }

    public void setDealOrderId(Long dealOrderId) {
        this.dealOrderId = dealOrderId;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDeal() {
        return deal;
    }

    public void setDeal(BigDecimal deal) {
        this.deal = deal;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getDealFee() {
        return dealFee;
    }

    public void setDealFee(BigDecimal dealFee) {
        this.dealFee = dealFee;
    }
}