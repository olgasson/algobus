package core.events;

/**
 * All possible EventTypes.
 * If new events are added to a bus, their event type needs to be defined
 * in this class. This is the only place where EventTypes should be defined.
 */
public class EventTypes {

    public static final int HEARTBEAT = 0;

    //ORDER RELATED EVENTS
    public static final int ORDER_CREATE = 1;
    public static final int ORDER_ACCEPT = 2;
    public static final int ORDER_REJECT = 3;
    public static final int ORDER_EXECUTE = 4;
    public static final int ORDER_CANCEL = 5;
    public static final int ORDER_CANCEL_REJECT = 6;
    public static final int ORDER_CANCEL_ACCEPT = 7;
    public static final int ORDER_REPLACE = 8;
    public static final int ORDER_REPLACE_REJECT = 9;
    public static final int ORDER_REPLACE_ACCEPT = 10;

    //BULK ORDER EVENTS
    public static final int ORDER_CREATE_BULK = 11;
    public static final int ORDER_REPLACE_BULK = 12;
    public static final int ORDER_REPLACE_BULK_REJECT = 13;
    public static final int ORDER_CREATE_BULK_REJECT = 14;

    //MARKET DATA EVENTS
    public static final int MD_BOOK_UPDATE = 21;
    public static final int MD_TRADE_UPDATE = 22;
    public static final int MD_BOOK_WIPE = 23;

    //SYSTEM EVENTS
    public static final int END_OF_SESSION = 31;

    //ANALYTIC EVENTS
    public static final int TRADING_FEE = 41;
    public static final int POSITION_UPDATE = 42;
    public static final int MD_BOOK_SNAPSHOT = 43;

}
