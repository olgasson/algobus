package core.events;


import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderReplaceReject event.
 *
 * This event can be used to indicate that an OrderReplace
 * Request has been rejected.
 */
public class OrderReplaceReject extends EventMessage {

    /** The orderId of the replacement */
    private long orderId;

    /** The orderId of the original order */
    private long originalOrderId;

    /** The destination */
    private long destination;

    /** The rejectReason */
    private byte rejectReason;

    @Override
    public void write() {
        super.write();
        putLong(orderId);
        putLong(originalOrderId);
        putLong(destination);
        putByte(rejectReason);
    }

    @Override
    public void read() {
        super.read();
        orderId = getLong();
        originalOrderId = getLong();
        destination = getLong();
        rejectReason = getByte();
    }

    public void clear() {
        orderId = 0;
        originalOrderId = 0;
        destination = 0;
        rejectReason = 0;
    }

    @Override
    public int type() {
        return EventTypes.ORDER_REPLACE_REJECT;
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

    public byte getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(byte rejectReason) {
        this.rejectReason = rejectReason;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ORDER_REPLACE_REJECT [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", orderId=" + orderId +
                ", originalOrderId=" + originalOrderId +
                ", rejectReason=" + Attributes.RejectReason.asString(rejectReason) +
                ']';
    }
}
