package info.jab.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructorComplexTest {

    @Test
    @DisplayName("Should create an instance when valid arguments are provided")
    void shouldCreateInstanceWithValidArguments() {
        //Given
        String prop1 = "value1";
        String prop2 = "value2";

        //When
        ConstructorComplex instance = new ConstructorComplex(prop1, prop2);

        //Then
        assertNotNull(instance);
        assertEquals(prop1, instance.getProperty1());
        assertEquals(prop2, instance.getProperty2());
    }

    @Test
    @DisplayName("Should throw NullPointerException when property1 is null")
    @SuppressWarnings("NullAway")
    void shouldThrowNPEWhenProperty1IsNull() {
        //Given
        String prop1 = null;
        String prop2 = "value2";

        //When & Then
        assertThatThrownBy(() -> new ConstructorComplex(prop1, prop2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Not valid property1");
    }

    @Test
    @DisplayName("Should throw NullPointerException when property2 is null")
    @SuppressWarnings("NullAway")
    void shouldThrowNPEWhenProperty2IsNull() {
        //Given
        String prop1 = "value1";
        String prop2 = null;

        //When & Then
        assertThatThrownBy(() -> new ConstructorComplex(prop1, prop2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Not valid property2");
    }
}
