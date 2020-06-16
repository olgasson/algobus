package bots;

import core.commands.Commander;
import core.common.Sources;
import core.events.*;
import io.mappedbus.MappedBusReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Base Bot has a MappedBusReader constantly polling from the shared
 * memory file. Consumers must override processBusMessage and handle
 * the EventTypes which they are interested in.
 *
 * The BaseBot also has a commander and offers write operations for EventWrite
 * Interface. That can be used to put Order }messages on the Bus.
 */
public abstract class BaseBot implements Runnable, RewindCompleteListener {
     /** The Logger */
    private static Logger logger = LogManager.getLogger("BaseBot");

    /** The source of this process */
    protected long source;

    /** The properties */
    protected static Properties properties = new Properties();

    /** The event reader */
    private MappedBusReader reader;

    /** The commander aka. event writer */
    protected Commander commander;

    /** Listeners will be notified when the rewind is complete */
    private ArrayList<RewindCompleteListener> rewindCompleteListeners = new ArrayList<>();

    /** true when we rewinded the stream */
    protected boolean isCaughtUp = false;

    /** Number of events processed so far */
    private long eventCounter = 0;

    /** bus file */
    private String busFileName;

    /** pre allocated size of the bus file */
    private long busFileSize;

    /** append to an exisiing bus file */
    private boolean appendFile;

    /** hmm */
    private boolean running = true;

    /** Some bots only write data */
    private final boolean needsReader;

    /** Resuable event objects */
    private OrderBookUpdate orderBookUpdate = new OrderBookUpdate();
    private TradeUpdate tradeUpdate = new TradeUpdate();
    private OrderBookWipe orderBookWipe = new OrderBookWipe();
    private OrderCreate orderCreate = new OrderCreate();
    private OrderAccept orderAccept = new OrderAccept();
    private OrderReject orderReject = new OrderReject();
    private OrderCancel orderCancel = new OrderCancel();
    private OrderCancelAccept orderCancelAccept = new OrderCancelAccept();
    private OrderCancelReject orderCancelReject = new OrderCancelReject();
    private OrderReplace orderReplace = new OrderReplace();
    private OrderReplaceAccept orderReplaceAccept = new OrderReplaceAccept();
    private OrderReplaceReject orderReplaceReject = new OrderReplaceReject();
    private OrderExecute orderExecute = new OrderExecute();

    private boolean readThrotteling;

    /**
     * Base Bot used for MarketData, Strategies, Gateways.
     * @param fileName
     * @param fileSize
     */
    public BaseBot(String fileName, long fileSize, String configFile, boolean needsReader) {
        this.needsReader = needsReader;
        this.busFileName = fileName;
        this.busFileSize = fileSize;

        try {
            FileInputStream in = new FileInputStream(configFile);
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.source = Sources.toLong(properties.getProperty("source"));
        this.appendFile = Boolean.parseBoolean(properties.getProperty("appendFile"));
        this.readThrotteling = Boolean.parseBoolean(properties.getProperty("readThrotteling", "true"));
        if (needsReader) {
            this.reader = new MappedBusReader(busFileName, busFileSize);
        }
        this.commander = new Commander(source, busFileName, busFileSize, appendFile);

        this.initBaseBot();

    }

    private void initBaseBot() {
        try {
            commander.open();
            if (needsReader) {
                reader.open();
            }
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void run() {
        try {

            //System.out.println("Opening Bus connection.");
            long msStart = System.currentTimeMillis();
            int emptyReadCounter = 0;
            while (running) {
                if (needsReader) {
                    if (reader.next()) {
                        boolean recovered = reader.hasRecovered();
                        if (recovered && !isCaughtUp) {
                            isCaughtUp = true;
                            long msEnd = System.currentTimeMillis();
                            logger.info("Caught up: " + eventCounter + " messages took " + (msEnd - msStart) + " ms.");
                            onRewindComplete();
                        }
                        int type = reader.readType();
                        processBusMessage(type, reader);
                        eventCounter++;
                        emptyReadCounter = 0;
                        if (eventCounter % 100000 == 0) {
                            logger.info( " Processed " + eventCounter + " messages - isCaughtUp: " + isCaughtUp);
                        }
                    }
                    else {
                        //there was nothing to read.
                        emptyReadCounter++;
                        if (readThrotteling && emptyReadCounter >= 10) {
                            //we want to throttle our reads, and we just had
                            //10 empty reads, so lets pause for a bit.
                            //this makes AWS a bit cheaper.
                            Thread.sleep(1);
                            emptyReadCounter = 0;
                        }
                    }

                } else {
                    Thread.sleep(1000);
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                commander.close();
                if (needsReader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRewindCompleteListener(RewindCompleteListener listener) {
        rewindCompleteListeners.add(listener);
    }

    @Override
    public void onRewindComplete() {
        for(RewindCompleteListener listener : rewindCompleteListeners) {
            listener.onRewindComplete();
        }
    }

    private void processBusMessage(int type, MappedBusReader reader) {
        switch (type) {
            case EventTypes.MD_BOOK_UPDATE:
                reader.readMessage(orderBookUpdate);
                onOrderBookUpdate(orderBookUpdate);
                break;
            case EventTypes.MD_TRADE_UPDATE:
                reader.readMessage(tradeUpdate);
                onTradeUpdate(tradeUpdate);
                break;
            case EventTypes.MD_BOOK_WIPE:
                reader.readMessage(orderBookWipe);
                onOrderBookWipe(orderBookWipe);
                break;
            case EventTypes.ORDER_CREATE:
                reader.readMessage(orderCreate);
                onOrderCreate(orderCreate);
                break;
            case EventTypes.ORDER_ACCEPT:
                reader.readMessage(orderAccept);
                onOrderAccept(orderAccept);
                break;
            case EventTypes.ORDER_REJECT:
                reader.readMessage(orderReject);
                onOrderReject(orderReject);
                break;
            case EventTypes.ORDER_CANCEL:
                reader.readMessage(orderCancel);
                onOrderCancel(orderCancel);
                break;
            case EventTypes.ORDER_CANCEL_ACCEPT:
                reader.readMessage(orderCancelAccept);
                onOrderCancelAccept(orderCancelAccept);
                break;
            case EventTypes.ORDER_CANCEL_REJECT:
                reader.readMessage(orderCancelReject);
                onOrderCancelReject(orderCancelReject);
                break;
            case EventTypes.ORDER_REPLACE:
                reader.readMessage(orderReplace);
                onOrderReplace(orderReplace);
                break;
            case EventTypes.ORDER_REPLACE_ACCEPT:
                reader.readMessage(orderReplaceAccept);
                onOrderReplaceAccept(orderReplaceAccept);
                break;
            case EventTypes.ORDER_REPLACE_REJECT:
                reader.readMessage(orderReplaceReject);
                onOrderReplaceReject(orderReplaceReject);
                break;
            case EventTypes.ORDER_EXECUTE:
                reader.readMessage(orderExecute);
                onOrderExecute(orderExecute);
                break;
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    public boolean isCaughtUp() {
        return isCaughtUp;
    }

    public void interrupt() {
        running = false;
    }

    public Commander getCommander() {
        return commander;
    }

    public static Properties getProperties() {
        return properties;
    }


    public abstract void onOrderBookUpdate(OrderBookUpdate orderBookUpdate);

    public abstract void onTradeUpdate(TradeUpdate tradeUpdate);

    public abstract void onOrderBookWipe(OrderBookWipe orderBookWipe);

    public abstract void onOrderCreate(OrderCreate orderCreate);

    public abstract void onOrderAccept(OrderAccept orderAccept);

    public abstract void onOrderReject(OrderReject orderReject);

    public abstract void onOrderCancel(OrderCancel orderCancel);

    public abstract void onOrderCancelAccept(OrderCancelAccept orderCancelAccept);

    public abstract void onOrderCancelReject(OrderCancelReject orderCancelReject);

    public abstract void onOrderReplace(OrderReplace orderReplace);

    public abstract void onOrderReplaceAccept(OrderReplaceAccept orderReplaceAccept);

    public abstract void onOrderReplaceReject(OrderReplaceReject orderReplaceReject);

    public abstract void onOrderExecute(OrderExecute orderExecute);

}
