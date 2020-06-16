package core.events;


import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderReplaceAccept event.
 *
 * This can be used to indicate that a replace request has been accepted.
 */
public class OrderReplaceAccept extends EventMessage {

    /** The orderId of the replacement */
    private long orderId;

    /** The destination */
    private long destination;

    /** The orderId of the original Order */
    private long originalOrderId;

    @Override
    public void write() {
        super.write();
        putLong(orderId);
        putLong(destination);
        putLong(originalOrderId);
    }

    @Override
    public void read() {
        super.read();
        orderId = getLong();
        destination = getLong();
        originalOrderId = getLong();
    }

    public void clear() {
        orderId = 0;
        destination = 0;
        originalOrderId = 0;
    }

    @Override
    public int type() {
        return EventTypes.ORDER_REPLACE_ACCEPT;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(long originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ORDER_REPLACE_ACCEPT [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ", originalOrderId=" + originalOrderId +
                ']';
    }
}
