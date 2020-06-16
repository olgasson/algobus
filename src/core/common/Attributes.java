package core.common;

public class Attributes {

    public static final class OrderBookUpdateType {

        public static final byte orderAdd = 1;
        public static final byte orderDelete = 2;
        public static final byte orderModify = 3;

        public static String asString(int orderBookUpdateType) {
            switch(orderBookUpdateType) {
                case orderAdd:
                    return "orderAdd";
                case orderDelete:
                    return "orderDelete";
                case orderModify:
                    return "orderModify";
                default:
                    return "";
            }
        }
    }

    public static final class OrderBookStyle {

        public static final byte idByOrder = 1;
        public static final byte idByLevel = 2;

        public static String asString(int orderBookUpdateType) {
            switch(orderBookUpdateType) {
                case idByOrder:
                    return "idByOrder";
                case idByLevel:
                    return "idByLevel";
                default:
                    return "";
            }
        }
    }

    public static final class RejectReason {

        public static final byte overload = 1;
        public static final byte unknown = 2;
        public static final byte ratelimit = 3;
        public static final byte invalidPrice = 4;

        public static String asString(byte rejectReason) {
            switch(rejectReason) {
                case overload:
                    return "overload";
                case unknown:
                    return "unknown";
                case ratelimit:
                    return "ratelimit";
                case invalidPrice:
                    return "invalidPrice";
                default:
                    return "";
            }
        }
    }

    public static final class CancelReason {

        public static final byte postOnly = 1;
        public static final byte unknown = 2;
        public static final byte user = 3;

        public static String asString(byte rejectReason) {
            switch(rejectReason) {
                case postOnly:
                    return "postOnly";
                case unknown:
                    return "unknown";
                case user:
                    return "user";
                default:
                    return "";
            }
        }
    }

     public static final class OrderState {

        public static final int pending = 1;
        public static final int working = 2;
        public static final int pendingReplace = 3;
        public static final int pendingCancel = 4;
        public static final int canceled = 5;
        public static final int done = 6;
        public static final int rejected = 7;
        public static final int filled = 8;

         public static boolean validateOrderStateModification(int currentState, int newState) {
             if (newState == pendingCancel) {
                 switch (currentState) {
                     case pending:
                     case pendingReplace:
                     case canceled:
                     case done:
                         return false;
                     case working:
                         return true;
                     default:
                         System.out.println("Trying to validate unknown state."+ currentState);
                         return false;
                 }
             }

             if (newState == pendingReplace) {
                 switch (currentState) {
                     case pending:
                     case pendingReplace:
                     case canceled:
                     case done:
                         return false;
                     case working:
                         return true;
                     default:
                         System.out.println("Trying to validate unknown state."+ currentState);
                         return false;
                 }
             }

             return false;
         }
     }

    /**
     * type	string	MARKET,
     * EXCHANGE MARKET,
     * LIMIT,
     * EXCHANGE LIMIT,
     * STOP,
     * EXCHANGE STOP,
     * TRAILING STOP,
     * EXCHANGE TRAILING STOP,
     * FOK,
     * EXCHANGE FOK,
     * STOP LIMIT,
     * EXCHANGE STOP LIMIT
     */
    public static final class OrderType {
        public static final int market = 1;
        public static final int limit = 2;
        public static final int trailingStop = 3;
        public static final int marginMarket = 4;
        public static final int marginLimit = 5;
        public static final int marginTrailingStop = 6;
        public static final int marketStop = 7;
        public static final int marginMarketStop = 8;
        public static final int stopLimit = 9;
        public static final int marginStopLimit = 10;

        public static String asString(int orderType) {
            switch (orderType) {
                case market:
                    return "EXCHANGE MARKET";
                case limit:
                    return "EXCHANGE LIMIT";
                case trailingStop:
                    return "EXCHANGE TRAILING STOP";
                case marginMarket:
                    return "MARKET";
                case marginLimit:
                    return "LIMIT";
                case marginTrailingStop:
                    return "TRAILING STOP";
                case marketStop:
                    return "EXCHANGE STOP";
                case marginMarketStop:
                    return "STOP";
                case stopLimit:
                    return "EXCHANGE STOP LIMIT";
                case marginStopLimit:
                    return "STOP LIMIT";

                default:
                    return "";

            }
        }
    }

    /**
     * Defines what role this order plays in a strategy.
     */
    public static final class OrderCategory {
        public static final int resting = 1;
        public static final int target = 2;
        public static final int stopLoss = 3;

        public static String asString(int orderCategory) {
            switch (orderCategory) {
                case resting:
                    return "resting";
                case target:
                    return "target";
                case stopLoss:
                    return "stopLoss";
                default:
                    return "";

            }
        }
    }

    public static final class Tif {
        /** OCO is one cancels the other used
         *  to place a stopLoss and
         * take profit order at the same time.
         */
        public static final int oco = 9;

        /**
         * tif	datetime string	Time-In-Force: datetime
         * for automatic order cancellation
         * (ie. 2020-01-01 10:45:23) )
         */
        public static final int datetime = 10;

        public static String asString(int tif) {
            switch (tif) {
                case oco:
                    return "oco";
                case datetime:
                    return "datetime";
                default:
                    return "";
            }
        }
    }

    public static final class Exchange {

        public static final int bitfinex = 1;
        public static final int bitmex = 2;
        public static final int binance = 3;

        public static String asString(int exchange) {
            switch (exchange) {
                case bitfinex:
                    return "bitfinex";
                case bitmex:
                    return "bitmex";
                case binance:
                    return "binance";
                default:
                    return "";
            }
        }

        public static int asInt(String exchange) {
            switch (exchange) {
                case "bitfinex":
                    return bitfinex;
                case "bitmex":
                    return bitmex;
                case "binance":
                    return binance;
                default:
                    return -1;
            }
        }
    }

    public static final class Side {

        public static final byte buy = 1;
        public static final byte sell = 2;


        public static String asString(byte side) {
            switch (side) {
                case buy:
                    return "buy";
                case sell:
                    return "sell";
                default:
                    return "";
            }
        }

        public static byte getOtherSide(byte side) {
            return side == buy ? sell : buy;
        }
    }


    public static final class CurrencyPair {

        public static final int btcusdt = 0;

        public static String asString(int currencyPair) {
            switch (currencyPair) {
                case btcusdt:
                    return "BTCUSDT";
                default:
                    return "";
            }
        }

        public static String asExchangeSymbol(int currencyPair, int exchange) {
            switch (currencyPair) {
                case btcusdt:
                    switch (exchange) {
                        case Exchange.bitfinex:
                            return "tBTCUST";
                        case Exchange.bitmex:
                            return "XBTUSD";
                        case Exchange.binance:
                            return "BTCUSDT";
                        default:
                            return "";
                    }
                default:
                    return "";
            }
        }

        public static int fromExchangeSymbol(String symbol) {
            switch (symbol) {
                case "BTCUSDT":
                case "tBTCUST":
                    return btcusdt;

                default:
                    return -1;
            }
        }

        public static int asInt(String currencyPair) {
            switch (currencyPair) {
                case "BTCUSDT":
                    return btcusdt;
                default:
                    return -1;
            }
        }


    }
}
