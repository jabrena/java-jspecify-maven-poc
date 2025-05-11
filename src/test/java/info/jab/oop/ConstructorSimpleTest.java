package info.jab.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructorSimpleTest {

    @Test
    @DisplayName("Should create an instance when valid arguments are provided")
    void should_createInstanceAndGetProperty1_when_validProperty1IsProvided() {
        // Given
        String property1 = "value";

        // When
        ConstructorSimple constructor = new ConstructorSimple(property1);

        // Then
        assertThat(constructor.getProperty1()).isEqualTo(property1);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when property1 is null")
    @SuppressWarnings("NullAway")
    void should_throwIllegalArgumentException_when_property1IsNull() {
        // Given
        String property1 = null;

        // When & Then
        assertThatThrownBy(() -> new ConstructorSimple(property1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Not valid property1");
    }
}
