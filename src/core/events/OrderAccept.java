package core.events;


import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderAccept event.
 * This event is used to indicate that a CreateOrder has
 * been accepted.
 */
public class OrderAccept extends EventMessage {

    /** The destination of this event*/
    private long destination;

    /** The orderId of the accepted order */
    private long orderId;

    /** Optional orderId assigned by the exchange */
    private long exchangeOrderId;

    public OrderAccept() {
    }

    @Override
    public void write() {
        super.write();
        putLong(destination);
        putLong(orderId);
        putLong(exchangeOrderId);

    }

    @Override
    public void read() {
        super.read();
        destination = getLong();
        orderId = getLong();
        exchangeOrderId = getLong();
    }

    @Override
    public int type() {
        return EventTypes.ORDER_ACCEPT;
    }

    public long getDestination() {
        return destination;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setExchangeOrderId(long exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public long getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ORDER_ACCEPT [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ", exchangeOrderId=" + exchangeOrderId +
                ']';
    }

    public void clear() {
        source = 0l;
        destination = 0l;
        orderId = 0l;
        exchangeOrderId = 0l;
        timestamp = 0l;
    }
}
