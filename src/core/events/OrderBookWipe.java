package core.events;


import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderBookWipe event.
 *
 * This event indicates that a certain orderBook should be wiped
 * clean / reset to empty.
 *
 * This should be used in case a MarketData processor disconnects from the
 * exchange or the quality of the marketdata published becomes bad. By doing that
 * a component consuming that marketdata will not operate on an outdated book.
 *
 */
public class OrderBookWipe extends EventMessage{


    public OrderBookWipe() {
    }

    @Override
    public void write() {
        super.write();
    }

    @Override
    public void read() {
        super.read();
    }

    @Override
    public int type() {
        return EventTypes.MD_BOOK_WIPE;
    }

    @Override
    public String toString() {
        return "ORDER_BOOK_WIPE [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ']';
    }

    public void clear() {
        source = 0l;
        timestamp = 0l;
    }
}
