package info.jab.demo;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConstructorTest {

    @Test
    void should_createInstanceAndGetName_when_validNameIsProvided() {
        // Given
        String name = "test";

        // When
        Constructor constructor = new Constructor(name);

        // Then
        assertThat(constructor.getName()).isEqualTo(name);
    }

    @Test
    void should_throwNullPointerException_when_nameIsNull() {
        // Given
        String name = null;

        // When & Then
        assertThatThrownBy(() -> new Constructor(name))
            .isInstanceOf(NullPointerException.class);
    }
}
