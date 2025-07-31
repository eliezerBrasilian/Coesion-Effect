package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageView_ extends ImageView {
    public ImageView_(Image image) {
        super(image);
        FXNodeContext.add(this);
    }

    public ImageView_(Image image,
            Consumer<InnerModifier> withModifier) {
        super(image);
        FXNodeContext.add(this);
        withModifier.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final ImageView_ node;

        public InnerModifier(ImageView_ node) {
            this.node = node;
        }

        public InnerModifier size(double size) {
            node.setFitWidth(size);
            node.setFitHeight(size);
            return this;
        }

        public InnerModifier animation(Consumer<ScaleTransition> config) {
            ScaleTransition transition = new ScaleTransition();
            transition.setNode(node);
            config.accept(transition); // aplica configuração passada pelo usuário
            transition.play();
            return this;
        }
    }
}
