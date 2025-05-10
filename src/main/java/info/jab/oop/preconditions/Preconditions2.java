package info.jab.oop.preconditions;

import java.util.Objects;

public class Preconditions2 {

    private final String property1;

    public Preconditions2(String property1) {
        if(Objects.isNull(property1)) {
            throw new IllegalArgumentException("Not valid property1");
        }
        this.property1 = property1;
    }

    public String getProperty1() {
        return property1;
    }
}
