package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import toolkit.theme.ThemeStyler;

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

        public InnerModifier font(javafx.scene.text.Font font) {
            node.setFont(font);
            return this;
        }

        public InnerModifier h1() {
            ThemeStyler.h1(node);
            return this;
        }

        public InnerModifier h2() {
            ThemeStyler.h2(node);
            return this;
        }

        public InnerModifier h3() {
            ThemeStyler.h3(node);
            return this;
        }

        public InnerModifier body() {
            ThemeStyler.body(node);
            return this;
        }

        public InnerModifier caption() {
            ThemeStyler.caption(node);
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

            public InnerStyles primary() {
                ThemeStyler.primary(mod.node);
                return this;
            }

            public InnerStyles secondary() {
                ThemeStyler.secondary(mod.node);
                return this;
            }

            public InnerStyles error() {
                ThemeStyler.error(mod.node);
                return this;
            }

            public InnerStyles onBackground() {
                ThemeStyler.onBackground(mod.node);
                return this;
            }

            public InnerStyles onSurface() {
                ThemeStyler.onSurface(mod.node);
                return this;
            }

            public InnerStyles disabled() {
                ThemeStyler.disabled(mod.node);
                return this;
            }
        }

    }
}
