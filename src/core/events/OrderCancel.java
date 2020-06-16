package core.events;


import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderCancel event.
 *
 * This event is used to request the cancellation of
 * an order.
 */
public class OrderCancel extends EventMessage {

    /** The destination */
    private long destination;

    /** The orderId */
    private long orderId;

    public OrderCancel() {
    }

    @Override
    public void write() {
        super.write();
        putLong(destination);
        putLong(orderId);
    }

    @Override
    public void read() {
        super.read();
        destination = getLong();
        orderId = getLong();
    }

    @Override
    public int type() {
        return EventTypes.ORDER_CANCEL;
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

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ORDER_CANCEL [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ']';
    }

    public void clear() {
        source = 0;
        destination = 0;
        orderId = 0;
    }
}
