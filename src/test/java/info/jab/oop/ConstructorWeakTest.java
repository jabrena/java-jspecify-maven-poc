package info.jab.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructorWeakTest {

    @Test
    @DisplayName("Should create an instance when valid arguments are provided")
    void should_createInstance_when_validArgumentsProvided() {
        // Given
        String prop1 = "property1";
        String prop2 = "property2";

        // When
        ConstructorWeak constructor = new ConstructorWeak(prop1, prop2);

        // Then
        assertThat(constructor.getProperty1()).isEqualTo(prop1);
        assertThat(constructor.getProperty2()).isEqualTo(prop2);
    }

    @Test
    @DisplayName("Should throw NullPointerException when property1 is null")
    @SuppressWarnings("NullAway")
    void should_throwNullPointerException_when_property1IsNull() {
        // Given
        String prop1 = null;
        String prop2 = "property2";

        // When & Then
        assertThatThrownBy(() -> new ConstructorWeak(prop1, prop2))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw NullPointerException when property2 is null")
    @SuppressWarnings("NullAway")
    void should_throwNullPointerException_when_property2IsNull() {
        // Given
        String prop1 = "property1";
        String prop2 = null;

        // When & Then
        assertThatThrownBy(() -> new ConstructorWeak(prop1, prop2))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should create an instance when empty strings are provided")
    void should_createInstance_when_emptyStringsProvided() {
        // Given
        String prop1 = "";
        String prop2 = "";

        // When
        ConstructorWeak constructor = new ConstructorWeak(prop1, prop2);

        // Then
        assertThat(constructor.getProperty1()).isEqualTo(prop1);
        assertThat(constructor.getProperty2()).isEqualTo(prop2);
    }
}
