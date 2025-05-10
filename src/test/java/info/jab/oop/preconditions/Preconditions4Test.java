package info.jab.oop.preconditions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Preconditions4Test {

    @Test
    void constructor_shouldSetProperty1_whenValidInput() {
        String value = "testValue";
        Preconditions4 obj = new Preconditions4(value);
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
