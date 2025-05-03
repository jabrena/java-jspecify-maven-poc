package info.jab.demo;

import java.util.Objects;

import org.jspecify.annotations.NonNull;

public class ConstructorWeak {
    //Non final fields
    private String property1;
    private String property2;

    public ConstructorWeak(@NonNull String property1, @NonNull String property2) {
        //Preconditions
        //I don´t like the exception that raise the method Objects.requireNonNull
        this.property1 = Objects.requireNonNull(property1);
        this.property2 = Objects.requireNonNull(property2);
    }

    //Weak constructor
    public ConstructorWeak(@NonNull String property1) {
       this.property1 = Objects.requireNonNull(property1);
    }

    public @NonNull String getProperty1() {
        return property1;
    }

    public @NonNull String getProperty2() {
        return property2;
    }
}
