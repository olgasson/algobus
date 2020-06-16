package core.state;


/**
 * Component internal Order Representation.
 */
public class Order {

    /** Source of the order */
    protected long source;

    /** Id of the order */
    protected long orderId;

    /** The parent id */
    private long parentId;

    /** OrderType */
    protected int orderType;

    /** Price */
    protected long price;

    /** Price */
    protected long secondaryPrice;

    /** Quantity */
    protected long quantity;

    /** Outstanding quantity on this order */
    protected long leavesQty;

    /** Timestamp do we need that ? */
    protected long timestamp;

    /** Side */
    protected byte side;

    /** Time in force */
    protected int tif;

    /** Symbol */
    protected int currencyPair;

    /** Current state of the order */
    protected int orderState;

    /** The order id assigned by the exchange */
    protected long exchangeOrderId;

    /** CaTegory of the order */
    protected int orderCategory;

    /** Indicates which level in a strategy layer this order is */
    protected int orderLevel;

    private String JSON;
    private long stopLoss;

    private long expiryTime;

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSecondaryPrice() {
        return secondaryPrice;
    }

    public void setSecondaryPrice(long secondaryPrice) {
        this.secondaryPrice = secondaryPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte getSide() {
        return side;
    }

    public void setSide(byte side) {
        this.side = side;
    }

    public int getTif() {
        return tif;
    }

    public void setTif(int tif) {
        this.tif = tif;
    }

    public int getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(int currencyPair) {
        this.currencyPair = currencyPair;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public long getLeavesQty() {
        return leavesQty;
    }

    public void setLeavesQty(long leavesQty) {
        this.leavesQty = leavesQty;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    public String getJSON() {
        return JSON;
    }

    public long getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(long exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public void setStopLoss(long stopLoss) {
        this.stopLoss = stopLoss;
    }

    public long getStopLoss() {
        return stopLoss;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getParentId() {
        return parentId;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public int getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(int orderLevel) {
        this.orderLevel = orderLevel;
    }

    @Override
    public String toString() {
        return "Order{" +
                "source=" + source +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", price=" + price +
                ", secondaryPrice=" + secondaryPrice +
                ", quantity=" + quantity +
                ", leavesQty=" + leavesQty +
                ", timestamp=" + timestamp +
                ", side=" + side +
                ", tif=" + tif +
                ", currencyPair=" + currencyPair +
                ", orderState=" + orderState +
                ", exchangeOrderId=" + exchangeOrderId +
                ", JSON='" + JSON + '\'' +
                ", stopLoss=" + stopLoss +
                ", parentId=" + parentId +
                ", expiryTime=" + expiryTime +
                ", orderCategory=" + orderCategory +
                ", orderLevel=" + orderLevel +
                '}';
    }

    public void clear() {
        source = 0l;
        orderId = 0;
        orderType = 0;
        orderState = 0;
        price = 0;
        secondaryPrice = 0;
        quantity = 0;
        timestamp = 0;
        currencyPair = 0;
        tif = 0;
        side = 0;
        leavesQty = 0;
        parentId = -1;
        stopLoss = 0;
        expiryTime = 0;
        orderCategory = 0;
        orderLevel = -1;
    }


}
