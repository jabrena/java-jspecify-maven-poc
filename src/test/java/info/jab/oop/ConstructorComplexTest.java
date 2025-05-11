package info.jab.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class ConstructorComplexTest {

    @Test
    @DisplayName("Should create an instance when valid arguments are provided")
    void should_createInstance_when_validArgumentsProvided() {
        // Given
        String prop1 = "value1";
        String prop2 = "value2";

        // When
        ConstructorComplex instance = new ConstructorComplex(prop1, prop2);

        // Then
        assertThat(instance).isNotNull();
        assertThat(instance.getProperty1()).isEqualTo(prop1);
        assertThat(instance.getProperty2()).isEqualTo(prop2);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when property1 is null")
    @SuppressWarnings("NullAway")
    void should_throwIllegalArgumentException_when_property1IsNull() {
        // Given
        String prop1 = null;
        String prop2 = "value2";

        // When & Then
        assertThatThrownBy(() -> new ConstructorComplex(prop1, prop2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Not valid property1");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when property2 is null")
    @SuppressWarnings("NullAway")
    void should_throwIllegalArgumentException_when_property2IsNull() {
        // Given
        String prop1 = "value1";
        String prop2 = null;

        // When & Then
        assertThatThrownBy(() -> new ConstructorComplex(prop1, prop2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Not valid property2");
    }
}
