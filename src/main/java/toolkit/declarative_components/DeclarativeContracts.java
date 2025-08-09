package toolkit.declarative_components;

import javafx.beans.value.ObservableValue;

public interface DeclarativeContracts<T> {
    void mountEffect(Runnable effect, ObservableValue<?>... dependencies);

}
