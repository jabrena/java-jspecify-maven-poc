package info.jab.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructorWeakTest {

    @Test
    @DisplayName("Should create an instance when valid arguments are provided")
    void shouldCreateInstanceWhenValidArgumentsProvided() {
        String prop1 = "property1";
        String prop2 = "property2";

        ConstructorWeak constructor = new ConstructorWeak(prop1, prop2);

        assertThat(constructor.getProperty1()).isEqualTo(prop1);
        assertThat(constructor.getProperty2()).isEqualTo(prop2);
    }

    @Test
    @DisplayName("Should throw NullPointerException when property1 is null")
    @SuppressWarnings("NullAway")
    void shouldThrowNullPointerExceptionWhenProperty1IsNull() {
        String prop1 = null;
        String prop2 = "property2";

        assertThatThrownBy(() -> new ConstructorWeak(prop1, prop2))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw NullPointerException when property2 is null")
    @SuppressWarnings("NullAway")
    void shouldThrowNullPointerExceptionWhenProperty2IsNull() {
        String prop1 = "property1";
        String prop2 = null;

        assertThatThrownBy(() -> new ConstructorWeak(prop1, prop2))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should create an instance when empty strings are provided")
    void shouldCreateInstanceWhenEmptyStringsProvided() {
        String prop1 = "";
        String prop2 = "";

        ConstructorWeak constructor = new ConstructorWeak(prop1, prop2);

        assertThat(constructor.getProperty1()).isEqualTo(prop1);
        assertThat(constructor.getProperty2()).isEqualTo(prop2);
    }
}
