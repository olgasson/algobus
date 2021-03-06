package core.events;

import core.common.Attributes;
import core.common.Sources;
import utils.DateUtils;
import utils.PriceUtils;

/**
 * An OrderCreate event.
 *
 * This event is used to create a new order.
 */
public class OrderCreate extends EventMessage {

    /** The destination */
    private long destination;

    /** The price */
    private long price;

    /** An optional secondaryPrice */
    private long secondaryPrice;

    /** The quantity */
    private long quantity;

    /** The currencyPair */
    private int  currencyPair;

    /** The side */
    private byte side;

    /** The orderType */
    private int orderType;

    /** The TimeInForce */
    private int tif;

    /** The orderId of the parent order */
    private long parentId;

    /** Optional expiryTime of the order */
    private long expiryTime;

    /** Optional orderCategory */
    private int orderCategory;

    /** Optional orderLevel */
    private int orderLevel;


    public OrderCreate() {

    }

    @Override
    public void write() {
        super.write();
        putLong(destination);
        putLong(price);
        putLong(secondaryPrice);
        putLong(quantity);
        putInt(currencyPair);
        putByte(side);
        putInt(orderType);
        putInt(tif);
        putLong(parentId);
        putLong(expiryTime);
        putInt(orderCategory);
        putInt(orderLevel);
    }

    @Override
    public void read() {
        super.read();
        destination = getLong();
        price = getLong();
        secondaryPrice = getLong();
        quantity = getLong();
        currencyPair = getInt();
        side = getByte();
        orderType = getInt();
        tif = getInt();
        parentId = getLong();
        expiryTime = getLong();
        orderCategory = getInt();
        orderLevel = getInt();
    }

    @Override
    public int type() {
        return EventTypes.ORDER_CREATE;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSecondaryPrice() {
        return secondaryPrice;
    }

    public void setSecondaryPrice(long secondaryPrice) {
        this.secondaryPrice = secondaryPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public int getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(int currencyPair) {
        this.currencyPair = currencyPair;
    }

    public byte getSide() {
        return side;
    }

    public void setSide(byte side) {
        this.side = side;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getTif() {
        return tif;
    }

    public void setTif(int tif) {
        this.tif = tif;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getParentId() {
        return parentId;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public int getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(int orderLevel) {
        this.orderLevel = orderLevel;
    }

    public void clear() {
        source = 0;
        destination = 0;
        price = 0;
        secondaryPrice = 0;
        quantity = 0;
        currencyPair = 0;
        side = 0;
        tif = 0;
        orderType = 0;
        parentId = -1;
        timestamp = 0l;
        expiryTime = 0l;
        orderCategory = 0;
        orderLevel = -1;
    }

    @Override
    public String toString() {
        return "ORDER_CREATE [ " +
                DateUtils.getFormattedTimestamp(timestamp) +
                ", source=" + Sources.asString(source) +
                ", destination=" + Sources.asString(destination) +
                ", price=" + PriceUtils.getPriceAsDouble(price) +
                ", secondaryPrice=" + PriceUtils.getPriceAsDouble(secondaryPrice) +
                ", quantity=" + PriceUtils.getQuantityAsDouble(quantity) +
                ", currencyPair=" + Attributes.CurrencyPair.asString(currencyPair) +
                ", side=" + Attributes.Side.asString(side) +
                ", orderType=" + Attributes.OrderType.asString(orderType) +
                ", tif=" + Attributes.Tif.asString(tif) +
                ", parentId=" + parentId +
                ", expiryTime=" + DateUtils.getFormattedTimestamp(expiryTime) +
                ", orderCategory=" + Attributes.OrderCategory.asString(orderCategory) +
                ", orderLevel=" + orderLevel +
                ']';
    }
}
