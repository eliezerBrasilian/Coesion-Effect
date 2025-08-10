package toolkit.declarative_components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import toolkit.declarative_components.modifiers.TextModifier;
import toolkit.declarative_components.modifiers.TextStyles;
import toolkit.theme.ThemeStyler;

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

    public void applyTheme() {
        ThemeStyler.textField(this);
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
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

    public static class InnerModifier extends TextModifier<TextField_> {
        public InnerModifier(TextField_ node) {
            super(node);
        }

        @Override
        public InnerModifier fontSize(double fontSize) {
            node.styles.put("-fx-font-size", fontSize + "px");
            node.updateStyles();
            return this;
        }

        @Override
        public InnerModifier font(Font font) {
            node.setFont(font);
            return this;
        }

        public InnerModifier applyTheme() {
            node.applyTheme();
            return this;
        }

        @Override
        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends TextStyles<TextField_> {
            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles color(Color color) {
                // TextField não tem método setTextFill, usa setStyle
                modifier.getNode()
                        .setStyle(modifier.getNode().getStyle() + "-fx-text-fill: " + colorToHex(color) + ";");
                return this;
            }

            @Override
            public InnerStyles fontWeight(String weight) {
                // TextField não tem método setTextFill, usa setStyle
                String currentStyle = modifier.getNode().getStyle();
                modifier.getNode().setStyle(
                        (currentStyle != null ? currentStyle : "") + "-fx-font-weight: " + weight + ";");
                return this;
            }

            public InnerStyles focusColor(String colorHex) {
                modifier.getNode().setfocusColor(colorHex);
                return this;
            }

            public InnerStyles borderRadius(int radius) {
                modifier.getNode().setBorderRadius(radius);
                return this;
            }

            public InnerStyles borderWidth(int width) {
                modifier.getNode().setBorderWidth(width);
                return this;
            }

            private String colorToHex(Color color) {
                return String.format("#%02X%02X%02X",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));
            }
        }
    }
}
