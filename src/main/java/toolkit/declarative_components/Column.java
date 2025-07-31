package toolkit.declarative_components;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Column extends VBox implements DeclarativeContracts<Column> {

    private double spacing = 10;
    private Insets padding = Insets.EMPTY;
    private Pos alignment = Pos.TOP_LEFT;

    public Column() {
        super();
    }

    public Column(Consumer<Column> content) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        content.accept(this);
        FXNodeContext.pop();
    }

    public Column(BiConsumer<Column, InnerModifier> withModifier) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.pop();
    }

    @Override
    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {
        effect.run();

        if (dependencies != null)
            for (ObservableValue<?> dep : dependencies) {
                dep.addListener((obs, oldVal, newVal) -> effect.run());
            }
    }

    @Override
    public void render(Consumer<Column> withModifier) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        withModifier.accept(this);
        FXNodeContext.pop();
    }

    public void render(BiConsumer<Column, InnerModifier> withModifier) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.pop();
    }

    public <T> void each(ObservableList<T> items, Function<T, Node> builder, Supplier<Node> renderIfEmpty) {
        VBox container = new VBox();
        getChildren().add(container);

        Runnable renderList = () -> {
            container.getChildren().clear();

            if (items.isEmpty()) {
                container.getChildren().add(renderIfEmpty.get());
            } else {
                for (T item : items) {
                    container.getChildren().add(builder.apply(item));
                }
            }

        };

        renderList.run();
        items.addListener((ListChangeListener<T>) change -> renderList.run());
    }

    public void when(ObservableValue<Boolean> condition, Supplier<Node> builder) {
        Node[] nodeRef = new Node[1]; // para guardar o nó criado

        // Listener que reage ao valor
        condition.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                if (nodeRef[0] == null) {
                    Node node = builder.get();
                    nodeRef[0] = node;
                    getChildren().add(node);
                }
            } else {
                if (nodeRef[0] != null) {
                    getChildren().remove(nodeRef[0]);
                    nodeRef[0] = null;
                }
            }
        });

        // estado inicial
        if (condition.getValue()) {
            Node node = builder.get();
            nodeRef[0] = node;
            getChildren().add(node);
        }
    }

    public void setSpacing_(double spacing) {
        this.spacing = spacing;
        requestLayout();
    }

    public void setPadding_(Insets padding) {
        this.padding = padding;
        requestLayout();
    }

    public void setAlignment_(Pos alignment) {
        this.alignment = alignment;
        requestLayout();
    }

    @Override
    protected void layoutChildren() {
        double contentWidth = getWidth() - padding.getLeft() - padding.getRight();
        double contentHeight = getHeight() - padding.getTop() - padding.getBottom();

        // Altura total dos filhos
        double totalHeight = 0;
        for (Node child : getChildren()) {
            totalHeight += child.prefHeight(-1);
        }
        totalHeight += (getChildren().size() - 1) * spacing;

        // Offset inicial vertical
        double yOffset;
        if (alignment == Pos.BOTTOM_CENTER || alignment == Pos.BOTTOM_LEFT || alignment == Pos.BOTTOM_RIGHT) {
            yOffset = contentHeight - totalHeight;
        } else if (alignment == Pos.CENTER || alignment == Pos.CENTER_LEFT || alignment == Pos.CENTER_RIGHT) {
            yOffset = (contentHeight - totalHeight) / 2;
        } else {
            yOffset = 0;
        }
        yOffset += padding.getTop();

        // Layout de cada filho
        for (Node child : getChildren()) {
            double childWidth;
            // Respeita fillMaxWidth (maxWidth == Double.MAX_VALUE)
            if (child.maxWidth(Double.MAX_VALUE) == Double.MAX_VALUE) {
                childWidth = contentWidth;
            } else {
                childWidth = Math.min(contentWidth, child.prefWidth(-1));
            }

            double childHeight = child.prefHeight(-1);

            Insets margin = VBox.getMargin(child);
            double topMargin = (margin != null) ? margin.getTop() : 0;

            double xOffset;
            if (childWidth == contentWidth) {
                // Preenche toda a largura -> começa do padding esquerdo
                xOffset = padding.getLeft();
            } else if (alignment == Pos.TOP_LEFT || alignment == Pos.CENTER_LEFT || alignment == Pos.BOTTOM_LEFT) {
                xOffset = padding.getLeft();
            } else if (alignment == Pos.TOP_RIGHT || alignment == Pos.CENTER_RIGHT || alignment == Pos.BOTTOM_RIGHT) {
                xOffset = padding.getLeft() + (contentWidth - childWidth);
            } else {
                xOffset = padding.getLeft() + (contentWidth - childWidth) / 2;
            }

            yOffset += topMargin; // aplica marginTop
            child.resizeRelocate(xOffset, yOffset, childWidth, childHeight);
            yOffset += childHeight + spacing;
        }
    }

    @Override
    protected double computePrefHeight(double width) {
        double totalHeight = padding.getTop() + padding.getBottom();
        for (Node child : getChildren()) {
            totalHeight += child.prefHeight(-1);
        }
        totalHeight += (getChildren().isEmpty() ? 0 : (getChildren().size() - 1) * spacing);
        return totalHeight;
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Column vbox;

        public InnerModifier(Column vbox) {
            this.vbox = vbox;
        }

        public InnerModifier alignment(Pos alignment) {
            vbox.setAlignment_(alignment);
            return this;
        }

        public InnerModifier spacing(double spacing) {
            vbox.setSpacing_(spacing);
            return this;
        }

        public InnerModifier padding(double all) {
            vbox.setPadding_(new Insets(all));
            return this;
        }

        public InnerModifier marginTop(double margin) {
            VBox.setMargin(vbox, new Insets(margin, 0, 0, 0));

            return this;
        }

        public InnerModifier padding(double top, double right, double bottom, double left) {
            vbox.setPadding(new Insets(top, right, bottom, left));
            return this;
        }

        public InnerModifier fillMaxHeigth(boolean b) {
            if (b) {
                vbox.setMaxHeight(Double.MAX_VALUE);
                VBox.setVgrow(vbox, Priority.ALWAYS);
            } else {
                vbox.setMaxHeight(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(vbox, Priority.NEVER);
            }
            return this;
        }

        public InnerModifier fillMaxWidth(boolean enable) {
            vbox.setFillWidth(enable);
            if (enable) {
                for (Node child : vbox.getChildren()) {
                    child.maxWidth(Double.MAX_VALUE); // <-- ajusta todos os filhos
                }
            }
            return this;
        }

        public InnerModifier maxHeight(double maxHeight) {
            vbox.setMaxHeight(maxHeight);
            return this;
        }

        public InnerModifier maxWidth(double maxWidth) {
            vbox.setMaxWidth(maxWidth);
            return this;
        }

        public InnerModifier height(double height) {
            vbox.setHeight(height);
            vbox.setPrefHeight(height);
            vbox.setMaxHeight(height);
            return this;
        }

        public InnerModifier width(double width) {
            vbox.setWidth(width);
            vbox.setPrefWidth(width);
            vbox.setMaxWidth(width);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles {
            private final InnerModifier mod;
            private CornerRadii cornerRadii = CornerRadii.EMPTY; // Armazena o raio dos cantos

            public InnerStyles(InnerModifier modifier) {
                this.mod = modifier;
            }

            public InnerStyles bgColor(Color color) {
                mod.vbox.setBackground(new Background(
                        new BackgroundFill(color,
                                cornerRadii, null)));
                return this;
            }

            public InnerStyles border(int radiusAll) {
                this.cornerRadii = new CornerRadii(radiusAll);

                mod.vbox.setBorder(new Border(
                        new BorderStroke(
                                null, // Color - você pode definir uma cor aqui (ex: Color.BLACK)
                                BorderStrokeStyle.SOLID, // Estilo da borda
                                cornerRadii, // Raio dos cantos
                                new BorderWidths(1) // Largura da borda (1 pixel por padrão)
                        )));

                // Se já houver um Background, reaplica com o novo CornerRadii
                if (mod.vbox.getBackground() != null) {
                    BackgroundFill currentFill = mod.vbox.getBackground().getFills().get(0);
                    mod.vbox.setBackground(new Background(
                            new BackgroundFill(
                                    currentFill.getFill(),
                                    cornerRadii,
                                    null)));
                }
                return this;
            }
        }

    }

}
