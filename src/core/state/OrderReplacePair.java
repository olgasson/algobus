package core.state;

public class OrderReplacePair {

    private Order original;
    private Order replacement;

    public OrderReplacePair(Order original, Order replacement) {
        this.original = original;
        this.replacement = replacement;
    }

    public Order getOriginal() {
        return original;
    }

    public void setOriginal(Order original) {
        this.original = original;
    }

    public Order getReplacement() {
        return replacement;
    }

    public void setReplacement(Order replacement) {
        this.replacement = replacement;
    }

}
