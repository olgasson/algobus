package core.commands;

import core.events.*;
import io.mappedbus.MappedBusWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;


/**
 * The commander is responsible to write events into the mapped bus file.
 * It also sets the source of every event that is being written.
 */
public class Commander extends MappedBusWriter implements EventWriter, MarketDataWriter {

    /** The logger */
    private static Logger logger = LogManager.getLogger("COMMAND");

    /** The source */
    private long source;

    /**
     * Constructor.
     * @param source The source of the application.
     * @param fileName The fileName of the mappedBus file.
     * @param fileSize The size of the mappedBus file.
     * @param append True, if we want to append to existing mappedBusFile.
     */
    public Commander(long source, String fileName, long fileSize, boolean append) {
        super(fileName, fileSize, append);
        this.source = source;
    }

    @Override
    public void orderCreate(OrderCreate orderCreate) {
        if (orderCreate == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderCreate.setSource(source);
            write(orderCreate);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderAccept(OrderAccept orderAccept) {
        if (orderAccept == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderAccept.setSource(source);
            write(orderAccept);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderReject(OrderReject orderReject) {
        if (orderReject == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderReject.setSource(source);
            write(orderReject);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderExecute(OrderExecute orderExecute) {
        if (orderExecute == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderExecute.setSource(source);
            write(orderExecute);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderCancel(OrderCancel orderCancel) {
        if (orderCancel == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderCancel.setSource(source);
            write(orderCancel);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderReplace(OrderReplace orderReplace) {
        if (orderReplace == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderReplace.setSource(source);
            write(orderReplace);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public void orderReplaceAccept(OrderReplaceAccept orderReplaceAccept) {
        if (orderReplaceAccept == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderReplaceAccept.setSource(source);
            write(orderReplaceAccept);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderCancelReject(OrderCancelReject orderCancelReject) {
        if (orderCancelReject == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderCancelReject.setSource(source);
            write(orderCancelReject);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderCancelAccept(OrderCancelAccept orderCancelAccept) {
        if (orderCancelAccept == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderCancelAccept.setSource(source);
            write(orderCancelAccept);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public void orderReplaceReject(OrderReplaceReject orderReplaceReject) {
        if (orderReplaceReject == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderReplaceReject.setSource(source);
            write(orderReplaceReject);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOrder(OrderBookUpdate orderBookUpdate) {
        if (orderBookUpdate == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderBookUpdate.setSource(source);
            write(orderBookUpdate);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyOrder(OrderBookUpdate orderBookUpdate) {
        if (orderBookUpdate == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderBookUpdate.setSource(source);
            write(orderBookUpdate);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(OrderBookUpdate orderBookUpdate) {
        if (orderBookUpdate == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderBookUpdate.setSource(source);
            write(orderBookUpdate);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void wipeOrderBook(OrderBookWipe orderBookWipe) {
        if (orderBookWipe == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            orderBookWipe.setSource(source);
            write(orderBookWipe);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTrade(TradeUpdate tradeUpdate) {
        if (tradeUpdate == null) {
            logger.error("No writer Initialized");
            return;
        }
        try {
            tradeUpdate.setSource(source);
            write(tradeUpdate);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public long getSource() {
        return source;
    }
}
