package core.state;


import bots.BaseBot;
import core.commands.Commander;
import core.common.Attributes;
import core.events.*;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import strategy.OrderManagerListener;

/**
 * Some kind of order manager.
 *
 */
public class OrderManager implements EventListener {

    /** The Logger */
    private static Logger logger = LogManager.getLogger("ORDMGR");

    /** The bot running it */
    private BaseBot bot;

    /** The commander of that bot */
    private Commander commander;

    /** starting orderid */
    private long orderId = 1l;

    /** OrderStore to keep track of all orders */
    private HashMap<Long, Order> orderStore = new HashMap<Long, Order>();

    /** Order Object pool - maybe there is a better way TODO */
    private OrderPool orderPool = new OrderPool();

    protected OrderCreate orderCreate = new OrderCreate();
    protected OrderAccept orderAccept = new OrderAccept();
    protected OrderCancel orderCancel = new OrderCancel();
    protected OrderReplace orderReplace = new OrderReplace();
    protected OrderReplaceAccept orderReplaceAccept = new OrderReplaceAccept();
    protected OrderReplaceReject orderReplaceReject = new OrderReplaceReject();
    protected OrderCancelAccept orderCancelAccept = new OrderCancelAccept();
    protected OrderCancelReject orderCancelReject = new OrderCancelReject();
    protected OrderExecute orderExecute = new OrderExecute();
    protected OrderReject orderReject = new OrderReject();

    private OrderManagerListener orderManagerListener;

    /** Constructor */
    public OrderManager(BaseBot bot) {
        this.bot = bot;
        this.commander = bot.getCommander();
    }

    public void setOrderManagerListener(OrderManagerListener orderManagerListener) {
        this.orderManagerListener = orderManagerListener;
    }

    /**
     * Creates a new orderId increment.
     * @return orderId
     */
    private long getOrderId() {
        return orderId++;
    }

    @Override
    public void onOrderCreate(OrderCreate orderCreate) {
        Order order = initOrder(getOrderId(),
                orderCreate.getCurrencyPair(),
                orderCreate.getPrice(),
                orderCreate.getSecondaryPrice(),
                orderCreate.getQuantity(),
                orderCreate.getSide(),
                orderCreate.getOrderType(),
                orderCreate.getSource(),
                orderCreate.getTif(),
                orderCreate.getParentId(),
                orderCreate.getExpiryTime(),
                orderCreate.getOrderCategory(),
                orderCreate.getOrderLevel());



        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderCreate.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderCreate: " + order.toString());
        }

        orderManagerListener.onOrderCreateImpl(order, orderCreate);

    }

    private Order initOrder(long orderId,
                            int currencyPair,
                            long price,
                            long secondaryPrice,
                            long quantity,
                            byte side,
                            int orderType,
                            long source,
                            int tif,
                            long parentId,
                            long expiryTime,
                            int orderCategory,
                            int orderLevel) {

        Order order = orderPool.checkOut();
        order.clear();

        //init the order
        order.setOrderId(orderId);
        order.setCurrencyPair(currencyPair);
        order.setOrderState(Attributes.OrderState.pending);
        order.setPrice(price);
        order.setSecondaryPrice(secondaryPrice);
        order.setQuantity(quantity);
        order.setSide(side);
        order.setOrderType(orderType);
        order.setSource(source);
        order.setTimestamp(System.currentTimeMillis());
        order.setTif(tif);
        order.setParentId(parentId);
        order.setExpiryTime(expiryTime);
        order.setOrderCategory(orderCategory);
        order.setOrderLevel(orderLevel);
        order.setLeavesQty(quantity);

        //add the order to the store.
        orderStore.put(orderId, order);
        return order;
    }

    @Override
    public void onOrderAccept(OrderAccept orderAccept) {
        Order order = orderStore.get(orderAccept.getOrderId());

        if (order == null) {
            logger.warn("OnOrderAccept cant find order: " + orderAccept.toString());
            return;
        }

        order.setOrderState(Attributes.OrderState.working);

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderAccept.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderAccept: " + orderAccept.toString());
        }

        orderManagerListener.onOrderAcceptImpl(order, orderAccept);
    }

    @Override
    public void onOrderReject(OrderReject orderReject) {
        Order order = orderStore.get(orderReject.getOrderId());

        if (order == null) {
            logger.warn("onOrderReject cant find order: " + orderReject.toString());
            return;
        }

        order.setOrderState(Attributes.OrderState.rejected);

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderReject.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderReject: " + orderReject.toString());
        }

        orderManagerListener.onOrderRejectImpl(order, orderReject);
    }

    @Override
    public void onOrderReplace(OrderReplace orderReplace) {

        OrderReplacePair pair = initOrderReplace(
                orderReplace.getParentId(),
                orderReplace.getPrice(),
                orderReplace.getSecondaryPrice(),
                orderReplace.getQuantity(),
                orderReplace.getExpiryTime());

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderReplace.getDestination()!= commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderReplace: " + orderReplace.toString());
        }

        orderManagerListener.onOrderReplaceImpl(pair.getOriginal(), pair.getReplacement(), orderReplace);

    }


    private OrderReplacePair initOrderReplace(long parentId, long price, long secondaryPrice, long quantity, long expiryTime) {
        Order replacement = orderPool.checkOut();
        Order original = orderStore.get(parentId);

        if (original == null) {
            logger.warn("OnPendingReplace cant find original: " + original);
            return null;
        }

        long orderId = getOrderId();

        //set the original to pending replace
        original.setOrderState(Attributes.OrderState.pendingReplace);

        //get a new orderId for the replace
        replacement.setOrderId(orderId);
        //set the old orderId as parent
        replacement.setParentId(parentId);

        replacement.setCurrencyPair(original.getCurrencyPair());
        replacement.setOrderState(Attributes.OrderState.pendingReplace);
        replacement.setPrice(price);
        replacement.setSecondaryPrice(secondaryPrice);
        replacement.setQuantity(quantity);
        replacement.setSide(original.getSide());
        replacement.setOrderType(original.getOrderType());
        replacement.setSource(original.getSource());
        replacement.setTimestamp(System.currentTimeMillis());
        replacement.setTif(original.getTif());
        replacement.setExpiryTime(expiryTime);
        replacement.setOrderCategory(original.getOrderCategory() );
        replacement.setOrderLevel(original.getOrderLevel());

        orderStore.put(replacement.getOrderId(), replacement);

        return new OrderReplacePair(original, replacement);
    }


    @Override
    public void onOrderReplaceAccept(OrderReplaceAccept orderReplaceAccept) {
        Order replacement = orderStore.get(orderReplaceAccept.getOrderId());
        Order original = orderStore.get(orderReplaceAccept.getOriginalOrderId());

        if (replacement == null || original == null) {
            logger.error("Unknown orderids onOrderReplaceAcceptImpl "+ orderReplaceAccept.toString());
            return;
        }

        //remove original order from store
        orderStore.remove(original);

        //change status of replacement order to working.
        replacement.setOrderState(Attributes.OrderState.working);

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderReplaceAccept.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderReplaceAccept: " + orderReplaceAccept.toString());
        }

        orderManagerListener.onOrderReplaceAcceptImpl(replacement, orderReplaceAccept);

    }

    @Override
    public void onOrderReplaceReject(OrderReplaceReject orderReplaceReject) {
        Order replacement = orderStore.get(orderReplaceReject.getOrderId());
        Order original = orderStore.get(orderReplaceReject.getOriginalOrderId());

        if (replacement == null || original == null) {
            logger.error("Unknown orderids orderReplaceReject "+ orderReplaceReject.toString());
            return;
        }

        //remove original order from store
        orderStore.remove(replacement);

        //change status of replacement order to working.
        original.setOrderState(Attributes.OrderState.working);

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderReplaceReject.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderReplaceReject: " + orderReplaceReject.toString());
        }

        orderManagerListener.onOrderReplaceRejectImpl(original, orderReplaceReject);
    }

    @Override
    public void onOrderExecute(OrderExecute orderExecute) {
        Order order = orderStore.get(orderExecute.getOrderId());

        if (order == null) {
            logger.warn("onOrderExecute cant find order: " + orderExecute.toString());
            return;
        }

        order.setLeavesQty(order.getLeavesQty() - orderExecute.getExecutedQty());

        if (order.getLeavesQty() > 0) {
            // its a partial fill, stays in working state.
            order.setOrderState(Attributes.OrderState.working);
            // update outstanding quantity.
            order.setLeavesQty(order.getLeavesQty() - orderExecute.getExecutedQty());
        }
        if (order.getLeavesQty() == 0) {
            //order is fully filled now.
            order.setOrderState(Attributes.OrderState.filled);
        }

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderExecute.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderExecute: " + orderExecute.toString());
        }

        orderManagerListener.onOrderExecutedImpl(order, orderExecute);

    }

    @Override
    public void onOrderCancel(OrderCancel orderCancel) {
        Order order = orderStore.get(orderCancel.getOrderId());

        if (order == null) {
            logger.warn("onOrderCancel cant find order: " + orderCancel.toString());
            return;
        }

        order.setOrderState(Attributes.OrderState.pendingCancel);

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderCancel.getDestination()!= commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderCancel: " + orderCancel.toString());
        }

        orderManagerListener.onOrderCancelImpl(order, orderCancel);
    }

    @Override
    public void onOrderCancelReject(OrderCancelReject orderCancelReject) {
        Order order = orderStore.get(orderCancelReject.getOrderId());

        if (order == null) {
            logger.warn("onOrderCancelReject cant find order: " + orderCancelReject.toString());
            return;
        }

        //pending cancel -> back to working.
        order.setOrderState(Attributes.OrderState.working);

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderCancelReject.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderCancelReject: " + orderCancelReject.toString());
        }

        orderManagerListener.onOrderCancelRejectImpl(order, orderCancelReject);
    }

    @Override
    public void onOrderCancelAccept(OrderCancelAccept orderCancelAccept) {
        Order order = orderStore.get(orderCancelAccept.getOrderId());

        if (order == null) {
            logger.warn("onOrderCancelAcceptImpl cant find order: " + orderCancelAccept.toString());
            return;
        }

        if (order.getOrderState() != Attributes.OrderState.pendingCancel) {
            //we have an unsolicited cancel
        }

        //pending cancel -> canceled.
        order.setOrderState(Attributes.OrderState.canceled);

        //remove the order from the store
        orderStore.remove(order.getOrderId());

        //after the store is updated, we check if its our own order
        //if not we dont want to notify our orderManagerListeners.
        if (orderCancelAccept.getDestination() != commander.getSource()) {
            return;
        }

        if (bot.isCaughtUp()) {
            logger.info("Receiving OrderCancelAccept: " + orderCancelAccept.toString());
        }

        orderManagerListener.onOrderCancelAcceptImpl(order, orderCancelAccept);
    }

    @Override
    public void onOrderBookWipe(OrderBookWipe orderBookWipe) {
        //nothing
    }

    public void createOrder(int orderType,
                            byte side,
                            long orderQty,
                            long price,
                            long secondaryPrice,
                            int currencyPair,
                            long destination,
                            int tif,
                            long parentId,
                            long expiryTime,
                            int orderCategory,
                            int orderLevel)
    {
        orderCreate.clear();
        orderCreate.setOrderType(orderType);

        if (orderType == Attributes.OrderType.market) {
            orderCreate.setPrice(0);
            orderCreate.setSecondaryPrice(0);
        }
        else {
            orderCreate.setPrice(price);
            orderCreate.setSecondaryPrice(secondaryPrice);
        }

        orderCreate.setQuantity(orderQty);
        orderCreate.setSide(side);
        orderCreate.setCurrencyPair(currencyPair);
        orderCreate.setDestination(destination);
        orderCreate.setTif(tif);
        orderCreate.setParentId(parentId);
        orderCreate.setExpiryTime(expiryTime);
        orderCreate.setOrderCategory(orderCategory);
        orderCreate.setOrderLevel(orderLevel);

        logger.info("Sending Order: " + orderCreate.toString());

        commander.orderCreate(orderCreate);
    }

    public Order getOrder(long orderId) {
        Order order = orderStore.get(orderId);

        if (order == null) {
            logger.warn("cant find order: " + orderId);
        }
        return order;
    }

    public void replaceOrder(long orderQty,
                             long price,
                             long originalOrderid,
                             long destination,
                             long expiryTime) {


        Order original = orderStore.get(originalOrderid);
        if (original == null) {
            logger.error("Cant replace an unknown order " + originalOrderid);
            return;
        }

        if (!Attributes.OrderState.validateOrderStateModification(original.getOrderState(), Attributes.OrderState.pendingCancel)) {
            System.out.println("Not allowed to replace Order in current state: " + original.toString());
            return;
        }

        orderReplace.clear();
        orderReplace.setCurrencyPair(original.getCurrencyPair());
        orderReplace.setParentId(originalOrderid);
        orderReplace.setQuantity(orderQty);
        orderReplace.setPrice(price);
        orderReplace.setTif(original.getTif());
        orderReplace.setDestination(destination);
        orderReplace.setOrderType(original.getOrderType());
        orderReplace.setSide(original.getSide());
        orderReplace.setExpiryTime(expiryTime);

        logger.info("Sending Order Replace: " + orderReplace.toString());

        commander.orderReplace(orderReplace);


    }

    public void cancelOrder(long orderId, long destination) {
        Order order = orderStore.get(orderId);

        if (order == null) {
            logger.warn("cant find order: " + orderId);
            return;
        }
        if (order.getOrderState() == Attributes.OrderState.pendingCancel) {
            logger.warn("Cannot cancel order in pendingCancel state " + order.toString());
            return;
        }

        orderCancel.clear();

        orderCancel.setOrderId(orderId);
        orderCancel.setDestination(destination);
        logger.info("Sending Cancel: " + orderCancel.toString());
        order.setOrderState(Attributes.OrderState.pendingCancel);
        commander.orderCancel(orderCancel);

    }

    public void orderAccept(long orderId) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            logger.error("Received OrderAccept for unknown order: " + orderId);
            return;
        }

        order.setOrderState(Attributes.OrderState.working);
        orderAccept.clear();
        orderAccept.setOrderId(order.getOrderId());
        orderAccept.setDestination(order.getSource());

        logger.info("Sending OrderAccept: " + orderAccept.toString());

        commander.orderAccept(orderAccept);
    }

    public void orderCancelAccept(long orderId, byte cancelReason) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            logger.error("Received OrderCancelAccept for unknown order: " + orderId);
            return;
        }
        order.setOrderState(Attributes.OrderState.canceled);
        orderCancelAccept.clear();
        orderCancelAccept.setOrderId(orderId);
        orderCancelAccept.setDestination(order.getSource());
        orderCancelAccept.setCancelReason(cancelReason);

        logger.info("Sending OrderCancelAccept: " + orderCancelAccept.toString());

        commander.orderCancelAccept(orderCancelAccept);
    }

    public void orderCancelReject(long orderId) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            logger.error("Received orderCancelReject for unknown order: " + orderId);
            return;
        }
        order.setOrderState(Attributes.OrderState.working);
        orderCancelReject.clear();
        orderCancelReject.setOrderId(orderId);
        orderCancelReject.setDestination(order.getSource());

        logger.info("Sending OrderCancelReject: " + orderCancelReject.toString());

        commander.orderCancelReject(orderCancelReject);
    }

    public void orderExecute(long orderId, long lastQty, long executedPrice, long leavesQty, long comission) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            logger.error("Received Execution for unknown order: " + orderId);
            return;
        }

        if (order.getOrderState() == Attributes.OrderState.filled) {
            logger.warn("Received Execution on already filled order " + orderId);
            return;
        }

        order.setLeavesQty(leavesQty);

        //send the execution event
        orderExecute.clear();
        orderExecute.setOrderId(orderId);
        orderExecute.setExecutedPrice(executedPrice);
        orderExecute.setExecutedQty(lastQty);
        orderExecute.setCurrencyPair(order.getCurrencyPair());
        orderExecute.setDestination(order.getSource());
        orderExecute.setSide(order.getSide());

        logger.info("Sending OrderExecute: " + orderExecute.toString());

        commander.orderExecute(orderExecute);
    }

    public void orderReplaceAccept(long orderId) {
        Order replacement = orderStore.get(orderId);
        Order original = orderStore.get(replacement.getParentId());
        if (replacement == null) {
            logger.error("Received ReplaceAccept for unknown order: " + orderId);
            return;
        }

        //replacement.setOrderState(Attributes.OrderState.working);
        orderReplaceAccept.clear();
        orderReplaceAccept.setOrderId(replacement.getOrderId());
        orderReplaceAccept.setOriginalOrderId(original.getOrderId());
        orderReplaceAccept.setDestination(replacement.getSource());

        logger.info("Sending OrderReplaceAccept: " + orderReplaceAccept.toString());

        commander.orderReplaceAccept(orderReplaceAccept);
    }

    public void orderReplaceReject(long orderId) {
        Order replacement = orderStore.get(orderId);
        Order original = orderStore.get(replacement.getParentId());
        if (replacement == null) {
            logger.error("Received ReplaceAccept for unknown order: " + orderId);
            return;
        }
        orderReplaceReject.clear();
        orderReplaceReject.setOrderId(replacement.getOrderId());
        orderReplaceReject.setOriginalOrderId(original.getOrderId());
        orderReplaceReject.setDestination(replacement.getSource());

        logger.info("Sending OrderReplaceReject: " + orderReplaceReject.toString());

        commander.orderReplaceReject(orderReplaceReject);
    }

    public void orderReject(long orderId, byte rejectReason) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            logger.error("Received OrderReject for unknown order: " + orderId);
            return;
        }

        orderReject.clear();
        orderReject.setOrderId(orderId);
        orderReject.setDestination(order.getSource());
        orderReject.setRejectReason(rejectReason);

        logger.info("Sending OrderReject: " + orderReject.toString());

        commander.orderReject(orderReject);
    }
}
