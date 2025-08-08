package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;

public interface DeclarativeContracts<T> {
    void mountEffect(Runnable effect, ObservableValue<?>... dependencies);

    void render(Consumer<T> withModifier);

    void render(Runnable content);
}
