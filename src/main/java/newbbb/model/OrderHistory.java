package newbbb.model;

import java.math.BigDecimal;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market == null ? null : market.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Byte getT() {
        return t;
    }

    public void setT(Byte t) {
        this.t = t;
    }

    public Byte getSide() {
        return side;
    }

    public void setSide(Byte side) {
        this.side = side;
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

    public BigDecimal getTakerFee() {
        return takerFee;
    }

    public void setTakerFee(BigDecimal takerFee) {
        this.takerFee = takerFee;
    }

    public BigDecimal getMakerFee() {
        return makerFee;
    }

    public void setMakerFee(BigDecimal makerFee) {
        this.makerFee = makerFee;
    }

    public BigDecimal getDealStock() {
        return dealStock;
    }

    public void setDealStock(BigDecimal dealStock) {
        this.dealStock = dealStock;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public BigDecimal getDealFee() {
        return dealFee;
    }

    public void setDealFee(BigDecimal dealFee) {
        this.dealFee = dealFee;
    }
}