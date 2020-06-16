package core.events;

public interface EventListener {

     void onOrderCreate(OrderCreate orderCreate);

     void onOrderAccept(OrderAccept orderAccept);

     void onOrderReject(OrderReject orderReject);

     void onOrderReplace(OrderReplace orderReplace);

     void onOrderReplaceAccept(OrderReplaceAccept orderReplaceAccept);

     void onOrderReplaceReject(OrderReplaceReject orderReplaceReject);

     void onOrderExecute(OrderExecute orderExecute);

     void onOrderCancel(OrderCancel orderCancel);

     void onOrderCancelReject(OrderCancelReject orderCancelReject);

     void onOrderCancelAccept(OrderCancelAccept orderCancelAccept);

     void onOrderBookWipe(OrderBookWipe orderBookWipe);

}
