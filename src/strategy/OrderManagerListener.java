package strategy;

import core.events.*;
import core.state.Order;

public interface OrderManagerListener {

    void onOrderAcceptImpl(Order order, OrderAccept orderAccept);

    void onOrderExecutedImpl(Order order, OrderExecute orderExecute);

    void onOrderCancelAcceptImpl(Order order, OrderCancelAccept orderCancelAccept);

    void onOrderCreateImpl(Order order, OrderCreate orderCreate);

    void onOrderReplaceAcceptImpl(Order order, OrderReplaceAccept orderReplaceAccept);

    void onOrderRejectImpl(Order order, OrderReject orderReject);

    void onOrderReplaceImpl(Order original, Order replacement, OrderReplace orderReplace);

    void onOrderReplaceRejectImpl(Order order, OrderReplaceReject orderReplaceReject);

    void onOrderCancelImpl(Order order, OrderCancel orderCancel);

    void onOrderCancelRejectImpl(Order order, OrderCancelReject orderCancelReject);

}
