package info.jab.demo;

import java.util.Objects;

import org.jspecify.annotations.NonNull;

public class ConstructorSimple {
    private final String property1;

    public ConstructorSimple(@NonNull String property1) {
        //Preconditions
        if(Objects.isNull(property1)) {
            throw new IllegalArgumentException("Not valid property1");
        }
        this.property1 = property1;
    }

    public @NonNull String getProperty1() {
        return property1;
    }
}
