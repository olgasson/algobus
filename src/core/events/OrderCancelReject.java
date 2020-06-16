package core.events;


import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderCancelReject event.
 *
 * This event is used to Reject a request of cancellation.
 */
public class OrderCancelReject extends EventMessage {

    /** The destination */
    private long destination;

    /** The orderId */
    private long orderId;

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
        return EventTypes.ORDER_CANCEL_REJECT;
    }

    public long getDestination() {
        return destination;
    }

    public void clear() {

    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ORDER_CANCEL_REJECT [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ']';
    }
}
