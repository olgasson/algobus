package core.common;

import java.util.HashSet;
import java.util.Set;

public abstract class ObjectPool<T> {

    private Set<T> available = new HashSet<>();
    private Set<T> inUse = new HashSet<>();

    protected abstract T create();
    protected abstract void clear(T instance);
    /**
     * Checkout object from pool
     */
    public synchronized T checkOut() {
        if (available.isEmpty()) {
            available.add(create());
        }

        T instance = available.iterator().next();

        available.remove(instance);
        inUse.add(instance);

        if(available.contains(instance)) {
            System.out.println("ERROR");
        }

        return instance;
    }

    public synchronized void release(T instance) {
        inUse.remove(instance);
        clear(instance);
        available.add(instance);
    }

    @Override
    public synchronized String toString() {
        return String.format("Pool available=%d inUse=%d", available.size(), inUse.size());
    }

    public void releaseAll() {
        for(T instance : inUse) {
            available.add(instance);
            clear(instance);
        }
        inUse.clear();
    }
}
