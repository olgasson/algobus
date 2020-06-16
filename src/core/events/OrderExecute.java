package core.events;


import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;
import utils.PriceUtils;

/**
 * An OrderExecute event.
 *
 * This event is used to indicate that an Order has been filled
 * at the exchange.
 */
public class OrderExecute extends EventMessage {

    /** The destination */
    private long destination;

    /** The orderId */
    private long orderId;

    /** The side */
    private byte side;

    /** The executedQuantity */
    private long executedQty;

    /** The executed Price */
    private long executedPrice;

    /** The currencyPair */
    private int currencyPair;

    @Override
    public void write() {
        super.write();
        putLong(destination);
        putLong(executedPrice);
        putLong(executedQty);
        putInt(currencyPair);
        putLong(orderId);
        putByte(side);
    }

    @Override
    public void read() {
        super.read();
        destination = getLong();
        executedPrice = getLong();
        executedQty = getLong();
        currencyPair = getInt();
        orderId = getLong();
        side = getByte();
    }

    @Override
    public int type() {
        return EventTypes.ORDER_EXECUTE;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public long getExecutedQty() {
        return executedQty;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setExecutedQty(long executedQty) {
        this.executedQty = executedQty;
    }

    public long getExecutedPrice() {
        return executedPrice;
    }

    public void setExecutedPrice(long executedPrice) {
        this.executedPrice = executedPrice;
    }

    public int getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(int currencyPair) {
        this.currencyPair = currencyPair;
    }

    public byte getSide() {
        return side;
    }

    public void setSide(byte side) {
        this.side = side;
    }

    public void clear() {
        source = 0;
        executedQty = 0;
        orderId = 0;
        destination = 0;
        executedPrice = 0;
        currencyPair = 0;
        timestamp = 0;
        side = 0;
    }

    @Override
    public String toString() {
        return "ORDER_EXECUTE [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ", side=" + Attributes.Side.asString(side) +
                ", executedQty=" + PriceUtils.getQuantityAsDouble(executedQty) +
                ", executedPrice=" + PriceUtils.getPriceAsDouble(executedPrice) +
                ", currencyPair=" + Attributes.CurrencyPair.asString(currencyPair) +
                ']';
    }
}
