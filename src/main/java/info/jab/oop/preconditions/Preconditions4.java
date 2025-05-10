package info.jab.oop.preconditions;

import com.google.common.base.Preconditions;

public class Preconditions4 {

    private final String property1;

    public Preconditions4(String property1) {
        this.property1 = Preconditions.checkNotNull(property1);
    }

    public String getProperty1() {
        return property1;
    }
}
