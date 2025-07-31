package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Label_ extends Label {
    public Label_(String value) {
        super(value);
        setPadding(Insets.EMPTY);
        FXNodeContext.add(this);
    }

    public Label_(String value, Consumer<InnerModifier> withModifier) {
        super(value);
        setPadding(Insets.EMPTY);
        FXNodeContext.add(this);
        withModifier.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Label node;

        public InnerModifier(Label node) {
            this.node = node;
        }

        public InnerModifier marginTop(double margin) {
            VBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        public InnerModifier alignment(TextAlignment alignment) {
            node.setTextAlignment(alignment);
            return this;
        }

        public InnerModifier fontSize(double fontSize) {
            node.setStyle("-fx-font-size: " + fontSize + "px;");
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

            public InnerStyles color(Color color) {
                mod.node.setTextFill(color);
                return this;
            }
        }

    }
}
