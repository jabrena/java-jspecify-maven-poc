package info.jab.oop;

import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.NonNull;

public class Generics {

    public static <T> T getFirstElement(T @NonNull [] items) {
        if (Objects.isNull(items) || items.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        return items[0];
    }

    public static <T> T getFirstElement(@NonNull List<T> items) {
        if (Objects.isNull(items) || items.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        return items.get(0);
    }
}
