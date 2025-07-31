package toolkit.declarative_components;

import java.util.function.BiConsumer;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Button_ extends Button {
    public Button_(String value) {
        super(value);
        FXNodeContext.add(this);
    }

    public Button_(String value, Runnable onClick) {
        super(value);
        setOnAction(ev -> {
            onClick.run();
        });
        FXNodeContext.add(this);
    }

    public Button_(String value, BiConsumer<Button_, InnerModifier> withModifier) {
        super(value);
        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.add(this);
    }

    public Button_(BiConsumer<Button_, InnerModifier> withModifier) {
        super();

        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.add(this);
    }

    public Button_(FontIcon icon, Runnable onClick) {
        setGraphic(icon);
        setOnAction(ev -> {
            onClick.run();
        });

        FXNodeContext.add(this);
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Button node;

        public InnerModifier(Button node) {
            this.node = node;
        }

        public InnerModifier marginTop(double margin) {
            VBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        public InnerModifier fillMaxWidth(boolean b) {
            if (b) {
                node.setMaxWidth(Double.MAX_VALUE);
                VBox.setVgrow(node, Priority.ALWAYS);
            } else {
                node.setMaxWidth(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(node, Priority.NEVER);
            }
            return this;
        }

        public InnerModifier onClick(Runnable onClick) {
            node.setOnAction(ev -> {
                onClick.run();
            });
            return this;
        }

        public InnerModifier icon(FontIcon icon) {
            node.setGraphic(icon);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles {
            private final InnerModifier mod;

            public InnerStyles(InnerModifier modifier) {
                this.mod = modifier;
            }

            public InnerStyles bgColor(Color color) {
                mod.node.setBackground(new Background(
                        new BackgroundFill(color, null, null)));
                return this;
            }

            public InnerStyles textColor(Color color) {
                mod.node.setTextFill(color);
                return this;
            }
        }

    }
}
