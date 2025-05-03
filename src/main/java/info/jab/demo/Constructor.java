package info.jab.demo;

import org.jspecify.annotations.NonNull;
import java.util.Objects;

public class Constructor {
    private final String name;

    public Constructor(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }
}
