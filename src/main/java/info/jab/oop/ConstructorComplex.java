package info.jab.oop;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class ConstructorComplex {

    private final String property1;
    private final String property2;

    public ConstructorComplex(String property1, String property2) {
        //Preconditions
        preconditions(property1, property2);

        this.property1 = property1;
        this.property2 = property2;
    }

    private void preconditions(String property1, String property2) {
        if(Objects.isNull(property1)) {
            throw new IllegalArgumentException("Not valid property1");
        }
        if(Objects.isNull(property2)) {
            throw new IllegalArgumentException("Not valid property2");
        }
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }
}
