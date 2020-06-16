package core.events;


import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;
import utils.PriceUtils;

/**
 * A TradeUpdate event.
 *
 * This event represents a trade from a marketData source.
 */
public class TradeUpdate extends EventMessage {

    /** The quantity */
    private long quantity;

    /** The price */
    private long price;

    /** The id */
    private int id;

    /** The side */
    private byte side;

    /** The currencyPair */
    private int currencyPair;

    /** The timestamp on the exchange */
    private long exchangeTimestamp;

    public TradeUpdate(long quantity, long price, int id, byte side, int currencyPair) {
        this.quantity = quantity;
        this.price = price;
        this.id = id;
        this.side = side;
        this.currencyPair = currencyPair;
    }

    public TradeUpdate() {

    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSide() {
        return side;
    }

    public void setSide(byte side) {
        this.side = side;
    }

    public int getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(int currencyPair) {
        this.currencyPair = currencyPair;
    }

    public long getExchangeTimestamp() {
        return exchangeTimestamp;
    }

    public void setExchangeTimestamp(long exchangeTimestamp) {
        this.exchangeTimestamp = exchangeTimestamp;
    }

    public void clear() {
        quantity = 0l;
        price = 0l;
        id = 0;
        source = 0;
        currencyPair = 0;
        timestamp = 0;
        exchangeTimestamp = 0;
    }

    @Override
    public String toString() {
        return "TRADE_UPDATE [" +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", exchangeTimestamp=" + DateUtils.getFormattedTimestamp(exchangeTimestamp) +
                ", source=" + Sources.asString(source) +
                ", currency=" + Attributes.CurrencyPair.asString(currencyPair) +
                ", quantity=" + PriceUtils.getQuantityAsDouble(quantity) +
                ", price=" + PriceUtils.getPriceAsDouble(price) +
                ", id=" + id +
                ", side=" + Attributes.Side.asString(side) +
                ']';
    }

    @Override
    public void write() {
        super.write();
        putLong(source);
        putLong(price);
        putLong(quantity);
        putInt(id);
        putByte(side);
        putInt(currencyPair);
        putLong(exchangeTimestamp);
    }

    @Override
    public void read() {
        super.read();
        source = getLong();
        price = getLong();
        quantity = getLong();
        id = getInt();
        side = getByte();
        currencyPair = getInt();
        exchangeTimestamp = getLong();
    }

    @Override
    public int type() {
        return EventTypes.MD_TRADE_UPDATE;
    }
}
