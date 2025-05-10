package info.jab.oop.preconditions;

public class Preconditions3 {

    private final String property1;

    public Preconditions3(String property1) {
        //Detect null adding null in the syntax, Ugly!
        assert property1 != null : "property1 must not be null";

        this.property1 = property1;
    }

    public String getProperty1() {
        return property1;
    }
}
