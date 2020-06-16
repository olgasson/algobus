package core.events;


import io.mappedbus.MappedBusMessage;

/**
 * This is a wrapper around th MappedBusMessage that serves as base class
 * for all Events. It forces a timestamp onto each event that indicates the
 * time of the write to the bus. It also holds the source of the writing
 * application.
 */
public abstract class EventMessage extends MappedBusMessage {

    /** The timestamp of the write */
    protected long timestamp;

    /** The source of the writing application */
    protected long source;

    @Override
    public void write() {
        timestamp = System.currentTimeMillis();
        putLong(timestamp);
        putLong(source);
    }

    @Override
    public void read() {
        timestamp = getLong();
        source = getLong();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public long getSource() {
        return source;
    }
}
