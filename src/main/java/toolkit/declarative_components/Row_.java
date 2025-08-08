package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Row_ extends HBox implements DeclarativeContracts<Row_.InnerModifier> {

    public Row_() {
        super();
        // Não expandir por padrão
        HBox.setHgrow(this, Priority.NEVER);
        setMaxWidth(Region.USE_PREF_SIZE);
    }

    public Row_(Runnable content) {
        this();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.run();
        FXNodeContext.pop();
    }

    public Row_(Consumer<InnerModifier> withModifier) {
        this();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        withModifier.accept(new InnerModifier(this));
        FXNodeContext.pop();
    }

    @Override
    public void render(Runnable content) {
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.run();
        FXNodeContext.pop();
    }

    @Override
    public void render(Consumer<InnerModifier> withModifier) {
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        withModifier.accept(new InnerModifier(this));
        FXNodeContext.pop();
    }

    @Override
    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {

    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public void setSpacing_(double spacing) {
        super.setSpacing(spacing);
        requestLayout();
    }

    public void setPadding_(Insets padding) {
        requestLayout();
    }

    public void setAlignment_(Pos alignment) {
        requestLayout();
    }

    public static class InnerModifier {
        private final Row_ container;

        public InnerModifier(Row_ container) {
            this.container = container;
        }

        public InnerModifier alignment(Pos alignment) {
            container.setAlignment_(alignment);
            return this;
        }

        public InnerModifier spaceBetween() {
            container.setAlignment(Pos.CENTER_LEFT);

            // Adiciona um listener para quando elementos forem adicionados
            container.getChildren().addListener((ListChangeListener<Node>) change -> {
                while (change.next()) {
                    if (change.wasAdded() && container.getChildren().size() == 2) {
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);
                        container.getChildren().add(1, spacer);
                    }
                }
            });
            return this;
        }

        public InnerModifier spacing(double spacing) {
            container.setSpacing_(spacing);
            return this;
        }

        public InnerModifier padding(double all) {
            container.setPadding_(new Insets(all));
            return this;
        }

        public InnerModifier marginTop(double margin) {
            HBox.setMargin(container, new Insets(margin, 0, 0, 0));

            return this;
        }

        public InnerModifier padding(double top, double right, double bottom, double left) {
            container.setPadding(new Insets(top, right, bottom, left));
            return this;
        }

        public InnerModifier fillMaxHeigth(boolean b) {
            if (b) {
                container.setMaxHeight(Double.MAX_VALUE);
                VBox.setVgrow(container, Priority.ALWAYS);
            } else {
                container.setMaxHeight(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(container, Priority.NEVER);
            }
            return this;
        }

        public InnerModifier fillMaxWidth(boolean b) {
            if (b) {
                container.setMaxWidth(Double.MAX_VALUE);
            } else {
                container.setMaxWidth(Region.USE_COMPUTED_SIZE);
            }
            return this;
        }

        public InnerModifier maxHeight(double maxHeight) {
            container.setMaxHeight(maxHeight);
            return this;
        }

        public InnerModifier maxWidth(double maxWidth) {
            container.setMaxWidth(maxWidth);
            return this;
        }

        public InnerModifier width(int width) {
            container.setPrefWidth(width);
            container.setMinWidth(width);
            container.setMaxWidth(width);
            HBox.setHgrow(container, Priority.NEVER); // <-- não deixar expandir
            return this;
        }

        public InnerModifier height(double height) {
            container.setPrefHeight(height);
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
                mod.container.setBackground(new Background(
                        new BackgroundFill(color, null, null)));
                return this;
            }

            public InnerStyles border(int radiusAll) {
                mod.container.setBorder(new Border(
                        new BorderStroke(
                                null, // Color - você pode definir uma cor aqui (ex: Color.BLACK)
                                BorderStrokeStyle.SOLID, // Estilo da borda
                                new CornerRadii(radiusAll), // Raio dos cantos
                                new BorderWidths(1) // Largura da borda (1 pixel por padrão)
                        )));
                return this;
            }
        }

    }

}