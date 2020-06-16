package core.events;


import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;

/**
 * An OrderBookUpdate event.
 * This event is used to write MarkeData onto the bus. It
 * indicates a change of an order or an order level in the exchange
 * orderbook. There are two styles of orderBook updates. One represents an
 * update on a single order, the other represents an update to a whole
 * orderLevel in the exchange order book.
 * {@link core.common.Attributes.OrderBookStyle}
 *
 * The OrderBookUpdate can be 3 different types:
 * {@link core.common.Attributes.OrderBookUpdateType}
 *
 * - Add an order or level.
 * - Modify an order or level.
 * - Delete an order or level.
 *
 */
public class OrderBookUpdate extends EventMessage {

    /** The update type of the orderBook */
    private byte orderBookUpdateType;

    /** The update id of an order or a level */
    private long id;

    /** Price */
    private long price;

    /** Quantity */
    private long quantity;

    /** Side */
    private byte side;

    /** Indicates if this update is part of a snapshot */
    private byte snapshot;

    /** The currency pair */
    private int currencyPair;

    /** The style of the orderbook */
    private byte orderBookStyle;

    /** Timestamp of the exchange */
    private long exchangeTimestamp;


    /**
     * Constructor.
     * @param orderBookUpdateType UpdateType, add, update or delete.
     * @param id The id of the order or the level.
     * @param price The price of the order of level.
     * @param quantity The quantity of the order or level.
     * @param side The side of the order or level.
     * @param snapshot Indicates if its part of a snapshot.
     * @param currencyPair The currencyPair of this update.
     */
    public OrderBookUpdate(byte orderBookUpdateType, int id, long price, long quantity, byte side, byte snapshot, int currencyPair) {
        this.orderBookUpdateType = orderBookUpdateType;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.snapshot = snapshot;
        this.currencyPair = currencyPair;
    }

    public OrderBookUpdate() {

    }

    public byte getOrderBookUpdateType() {
        return orderBookUpdateType;
    }

    public void setOrderBookUpdateType(byte orderBookUpdateType) {
        this.orderBookUpdateType = orderBookUpdateType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public byte getSide() {
        return side;
    }

    public void setSide(byte side) {
        this.side = side;
    }

    public byte getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(byte snapshot) {
        this.snapshot = snapshot;
    }

    public int getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(int currencyPair) {
        this.currencyPair = currencyPair;
    }

    public byte getOrderBookStyle() {
        return orderBookStyle;
    }

    public void setOrderBookStyle(byte orderBookStyle) {
        this.orderBookStyle = orderBookStyle;
    }

    public long getExchangeTimestamp() {
        return exchangeTimestamp;
    }

    public void setExchangeTimestamp(long exchangeTimestamp) {
        this.exchangeTimestamp = exchangeTimestamp;
    }

    @Override
    public String toString() {
        return "ORDER_BOOK_UPDATE [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", exchangeTimestamp=" + DateUtils.getFormattedTimestamp(exchangeTimestamp) +
                ", source=" + Sources.asString(source) +
                ", orderBookUpdateType=" + Attributes.OrderBookUpdateType.asString(orderBookUpdateType) +
                ", orderBookStyle=" + Attributes.OrderBookStyle.asString(orderBookStyle) +
                ", id=" + id +
                ", currency=" + Attributes.CurrencyPair.asString(currencyPair) +
                ", price=" + price +
                ", quantity=" + quantity +
                ", side=" + Attributes.Side.asString(side) +
                ", snapshot=" + snapshot +
                ']';
    }

    public void clear() {
        orderBookUpdateType = -1;
        id = 0l;
        price = 0l;
        quantity = 0l;
        timestamp = 0l;
        side = 0;
        snapshot = -1;
        source = 0l;
        currencyPair = 0;
        orderBookStyle = -1;
        exchangeTimestamp = 0;
    }

    @Override
    public void write() {
        super.write();
        putInt(currencyPair);
        putLong(id);
        putLong(price);
        putLong(quantity);
        putByte(side);
        putByte(snapshot);
        putByte(orderBookUpdateType);
        putByte(orderBookStyle);
        putLong(exchangeTimestamp);
    }

    @Override
    public void read() {
        super.read();
        currencyPair = getInt();
        id = getLong();
        price = getLong();
        quantity = getLong();
        side = getByte();
        snapshot = getByte();
        orderBookUpdateType = getByte();
        orderBookStyle = getByte();
        exchangeTimestamp = getLong();
    }

    @Override
    public int type() {
        return EventTypes.MD_BOOK_UPDATE;
    }
}
