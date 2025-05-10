package info.jab.oop.preconditions;

import java.util.Objects;

public class Preconditions1 {

    private final String property1;

    public Preconditions1(String property1) {
        this.property1 = Objects.requireNonNull(property1);
    }

    public String getProperty1() {
        return property1;
    }
}
