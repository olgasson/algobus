package core.events;


import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;

/**
 * An orderReject event.
 *
 * This can be used to indicate that an OrderCreate
 * was rejected.
 */
public class OrderReject extends EventMessage {

    /** The destination */
    private long destination;

    /** The orderId */
    private long orderId;

    /** The rejectReason */
    private byte rejectReason;

    @Override
    public void write() {
        super.write();
        putLong(destination);
        putLong(orderId);
        putByte(rejectReason);
    }

    @Override
    public void read() {
        super.read();
        destination = getLong();
        orderId = getLong();
        rejectReason = getByte();
    }

    @Override
    public int type() {
        return EventTypes.ORDER_REJECT;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public byte getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(byte rejectReason) {
        this.rejectReason = rejectReason;
    }

    public void clear() {
        orderId = 0;
        timestamp = 0;
        source = 0;
        destination = 0;
        rejectReason = 0;
    }

    @Override
    public String toString() {
        return "ORDER_REJECT [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ", rejectReason=" + Attributes.RejectReason.asString(rejectReason) +
                ']';
    }
}
