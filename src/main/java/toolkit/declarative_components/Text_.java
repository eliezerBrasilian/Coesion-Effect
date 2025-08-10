package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import toolkit.theme.ThemeStyler;

public class Text_ extends Text {
    public Text_(String value) {
        super(value);
        FXNodeContext.add(this);
    }

    public Text_(String value, Consumer<InnerModifier> withModifier) {
        this(value);
        withModifier.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Text_ node;

        public InnerModifier(Text_ node) {
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

        public InnerModifier maxWidth(double maxWidth) {
            node.setWrappingWidth(maxWidth);
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
                mod.node.setFill(color);
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

            public InnerStyles fontWeight(String weight) {
                String currentStyle = mod.node.getStyle();
                mod.node.setStyle(
                        (currentStyle != null ? currentStyle : "") + "-fx-font-weight: " + weight + ";");
                return this;
            }

            public InnerStyles fontWeightBold() {
                return fontWeight("bold");
            }

            public InnerStyles fontWeightNormal() {
                return fontWeight("normal");
            }
        }

    }
}
