package core.commands;

import core.events.*;

public interface EventWriter {

    void orderCreate(OrderCreate orderCreate);

    void orderAccept(OrderAccept orderAccept);

    void orderReject(OrderReject orderReject);

    void orderExecute(OrderExecute orderExecute);

    void orderCancel(OrderCancel orderCancel);

    void orderReplace(OrderReplace orderReplace);

    void orderCancelAccept(OrderCancelAccept orderCancelAccept);

    void orderCancelReject(OrderCancelReject orderCancelReject);
}
