package core.state;


import core.common.ObjectPool;

public class OrderPool extends ObjectPool<Order> {

    @Override
    protected Order create() {
        return new Order();
    }

    @Override
    protected void clear(Order instance) {
        instance.clear();
    }
}
