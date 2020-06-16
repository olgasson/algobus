package core.common;

/**
 * Probably there is a better way of doing this....
 */
public enum Sources {

    //Binance Gateway
    G_BIN,
    //Binance MarketData
    M_BIN,
    //Binance Strategy
    S_BIN,

    //Bitfinex Gateway
    G_BFX,
    //Bitfinex MarketData
    M_BFX,
    //Bitfinex Strategy
    S_BFX,

    //Bitmex Gateway
    G_BMX,
    //Bitmex MarketData
    M_BMX,
    //Bitmex Strategy
    S_BMX;


    Sources() {
    }

    public long value() {
        return ByteUtils.bytesToLong(name().getBytes());
    }

    public static String asString(long source) {
        return ByteUtils.longToString(source);
    }

    public static long toLong(String source) {
        return ByteUtils.bytesToLong(source.getBytes());
    }

    public static Sources valueOf(long source) {
        return valueOf(ByteUtils.longToString(source));
    }
}