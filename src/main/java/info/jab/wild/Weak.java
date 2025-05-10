package info.jab.wild;

public class Weak {
    private String property1;
    private String property2;

    public Weak(String property1) {
        this.property1 = property1;
        this.property1 = "property2";
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }
}