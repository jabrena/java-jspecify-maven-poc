package info.jab.oop.preconditions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Preconditions3Test {

    @Test
    void constructor_shouldSetProperty1_whenValidInput() {
        String value = "testValue";
        Preconditions3 obj = new Preconditions3(value);
        assertEquals(value, obj.getProperty1());
    }

    /*
    Impossible to test
    @Test
    void constructor_shouldThrowNullPointerException_whenNullInput() {
        assertThrows(NullPointerException.class, () -> new Preconditions1(null));
    }
    */
}
