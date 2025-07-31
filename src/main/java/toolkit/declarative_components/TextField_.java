package toolkit.declarative_components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TextField_ extends TextField {
    private final Map<String, String> styles = new HashMap<>();

    // modifier->placeholder

    public TextField_() {
        super();
        defaultFocusColor();
        FXNodeContext.add(this);
    }

    public TextField_(String value) {
        this();
        setPadding(Insets.EMPTY);
        setMaxWidth(Double.MAX_VALUE);
    }

    public TextField_(String value, Consumer<InnerModifier> withModifier) {
        this();
        setPadding(Insets.EMPTY);
        setMaxWidth(Double.MAX_VALUE);
        withModifier.accept(new InnerModifier(this));
    }

    public TextField_(String placeholder, SimpleStringProperty input) {
        this();
        this.setPromptText(placeholder);
        this.textProperty().bindBidirectional(input);
    }

    private void updateStyles() {
        StringBuilder sb = new StringBuilder();
        styles.forEach((k, v) -> sb.append(k).append(": ").append(v).append(";"));
        setStyle(sb.toString());
    }

    public void defaultFocusColor() {
        setfocusColor("#000");
        setBorderWidth(1);
        setBorderRadius(3);
    }

    public void setfocusColor(String colorHex) {
        styles.put("-fx-focus-color", colorHex);
        styles.put("-fx-faint-focus-color", "transparent");
        updateStyles();
    }

    public void setBorderWidth(int width) {
        styles.put("-fx-background-insets", "0, 1");
        styles.put("-fx-border-width", width + "");
        updateStyles();
    }

    public void setBorderRadius(int radius) {
        styles.put("-fx-border-radius", radius + "");
        updateStyles();
    }

    public static class InnerModifier {
        private final TextField_ node;

        public InnerModifier(TextField_ node) {
            this.node = node;
        }

        public InnerModifier marginTop(double margin) {
            VBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        public InnerModifier fontSize(double fontSize) {
            node.styles.put("-fx-font-size", fontSize + "px");
            node.updateStyles();
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

            public InnerStyles focusColor(String colorHex) {
                mod.node.setfocusColor(colorHex);
                return this;
            }

            public InnerStyles borderRadius(int radius) {
                mod.node.setBorderRadius(radius);
                return this;
            }

            public InnerStyles borderWidth(int width) {
                mod.node.setBorderWidth(width);
                return this;
            }
        }
    }
}
