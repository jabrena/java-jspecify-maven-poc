package info.jab.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenericsTest {

    @Test
    @DisplayName("Should return the first element of a non-empty array")
    void should_returnFirstElement_when_arrayIsNotEmpty() {
        // Given
        Integer[] items = {1, 2, 3};

        // When
        Integer result = Generics.getFirstElement(items);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when array is null or empty")
    void should_throwException_when_arrayIsNullOrEmpty() {
        // Given
        Integer[] emptyArray = new Integer[0];

        // When & Then
        assertThatThrownBy(() -> Generics.getFirstElement(emptyArray))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Array is empty");
    }

    @Test
    @DisplayName("Should return the first element of a non-empty list")
    void should_returnFirstElement_when_listIsNotEmpty() {
        // Given
        List<String> items = Arrays.asList("a", "b", "c");

        // When
        String result = Generics.getFirstElement(items);

        // Then
        assertThat(result).isEqualTo("a");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when list is null or empty")
    void should_throwException_when_listIsNullOrEmpty() {
        // Given
        List<String> emptyList = Collections.emptyList();

        // When & Then
        assertThatThrownBy(() -> Generics.getFirstElement(emptyList))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("List is empty");
    }
}
