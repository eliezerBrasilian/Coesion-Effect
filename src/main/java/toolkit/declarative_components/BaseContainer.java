package toolkit.declarative_components;

import java.util.function.Predicate;
import java.util.function.Supplier;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class BaseContainer extends VBox implements DeclarativeContracts<BaseContainer> {

    /**
     * Renderiza conteúdo condicionalmente, observando um ObservableValue<T>.
     * 
     * @param observable valor observado
     * @param predicate  condição para mostrar o conteúdo (ex: v -> v != null e
     *                   v.equals("João"))
     * @param onTrue     Supplier que cria o conteúdo quando condição é true
     * @param <T>        tipo do valor observado
     * @return RenderIfHandler para encadeamento (otherwise)
     */
    public <T> RenderIfHandler<T> renderIf(
            ObservableValue<T> observable,
            Predicate<T> predicate,
            Supplier<Node> onTrue) {
        RenderIfHandler<T> handler = new RenderIfHandler<>(this, observable, predicate, onTrue);
        handler.update(observable.getValue());
        observable.addListener((obs, oldVal, newVal) -> handler.update(newVal));
        return handler;
    }

    /**
     * Sobrecarga para ObservableValue<Boolean> simples (true/false)
     */
    public RenderIfHandler<Boolean> renderIf(
            ObservableValue<Boolean> observable,
            Supplier<Node> onTrue) {
        return renderIf(observable, b -> b != null && b, onTrue);
    }

    /**
     * Sobrecarga para valor booleano fixo, não-observável
     */
    public RenderIfHandler<Boolean> renderIf(
            boolean condition,
            Supplier<Node> onTrue) {
        return renderIf(new SimpleBooleanProperty(condition), b -> b, onTrue);
    }

    /**
     * Classe que controla o renderIf e otherwise
     */
    public static class RenderIfHandler<T> {
        private final BaseContainer container;
        private final ObservableValue<T> observable;
        private final Predicate<T> predicate;
        private final Supplier<Node> onTrueSupplier;

        private Supplier<Node> onFalseSupplier;
        private Node currentNode;

        public RenderIfHandler(
                BaseContainer container,
                ObservableValue<T> observable,
                Predicate<T> predicate,
                Supplier<Node> onTrueSupplier) {
            this.container = container;
            this.observable = observable;
            this.predicate = predicate;
            this.onTrueSupplier = onTrueSupplier;
        }

        /** Atualiza o nó renderizado conforme o valor */
        private void update(T value) {
            boolean shouldRenderTrue = predicate.test(value);

            Node newNode = shouldRenderTrue
                    ? (onTrueSupplier != null ? onTrueSupplier.get() : null)
                    : (onFalseSupplier != null ? onFalseSupplier.get() : null);

            if (currentNode != null) {
                container.getChildren().remove(currentNode);
                currentNode = null;
            }

            if (newNode != null) {
                container.getChildren().add(newNode);
                currentNode = newNode;
            }
        }

        /** Define o bloco a ser renderizado quando a condição for falsa */
        public RenderIfHandler<T> otherwise(Supplier<Node> onFalse) {
            this.onFalseSupplier = onFalse;
            // Atualiza para o estado atual da propriedade
            update(observable.getValue());
            return this;
        }
    }
}
