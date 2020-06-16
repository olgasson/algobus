package core.events;


import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderCancelAccept event
 *
 * This event is used to accept a cancellation request
 * of an order.
 *
 * It can also be used to force cancel an order, without
 * prior request of cancellation.
 */
public class OrderCancelAccept extends EventMessage {

    /** The destination */
    private long destination;

    /** The orderId */
    private long orderId;

    /** The reason of the cancellation */
    private byte cancelReason;

    @Override
    public void write() {
        super.write();
        putLong(destination);
        putLong(orderId);
        putByte(cancelReason);
    }

    @Override
    public void read() {
        super.read();
        destination = getLong();
        orderId = getLong();
        cancelReason = getByte();
    }

    @Override
    public int type() {
        return EventTypes.ORDER_CANCEL_ACCEPT;
    }

    public long getDestination() {
        return destination;
    }

    public void clear() {
        destination = 0;
        orderId = 0;
        cancelReason = 0;
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

    public byte getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(byte cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String toString() {
        return "ORDER_CANCEL_ACCEPT [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ", cancelReason=" + Attributes.CancelReason.asString(cancelReason) +
                ']';
    }
}
