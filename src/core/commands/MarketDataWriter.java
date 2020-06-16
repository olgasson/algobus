package core.commands;

import core.events.OrderBookUpdate;
import core.events.OrderBookWipe;
import core.events.TradeUpdate;

public interface MarketDataWriter {

    void addOrder(OrderBookUpdate orderBookUpdate);

    void modifyOrder(OrderBookUpdate orderBookUpdate);

    void deleteOrder(OrderBookUpdate orderBookUpdate);

    void wipeOrderBook(OrderBookWipe orderBookWipe);

    void addTrade(TradeUpdate tradeUpdate);

}
