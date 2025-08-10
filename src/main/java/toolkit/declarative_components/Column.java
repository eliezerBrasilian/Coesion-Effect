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
import javafx.scene.paint.Paint;
import toolkit.declarative_components.modifiers.LayoutModifier;
import toolkit.declarative_components.modifiers.LayoutStyles;

public class Column extends VBox implements DeclarativeContracts<Column.InnerModifier> {

    public Column() {
        super();

        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);

        // 🔑 Importante: impedir crescimento automático
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    public Column(Runnable content) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        content.run();
        FXNodeContext.pop();
        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);

        // 🔑 Importante: impedir crescimento automático
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    public Column(Consumer<InnerModifier> content) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        content.accept(new InnerModifier(this));
        FXNodeContext.pop();
        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);

        // 🔑 Importante: impedir crescimento automático
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    public Column(BiConsumer<Column, InnerModifier> withModifier) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.pop();
        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);

        // 🔑 Importante: impedir crescimento automático
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    @Override
    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {
        effect.run();

        if (dependencies != null)
            for (ObservableValue<?> dep : dependencies) {
                dep.addListener((obs, oldVal, newVal) -> effect.run());
            }
    }

    public <T> void each(ObservableList<T> items, Function<T, Node> builder, Supplier<Node> renderIfEmpty) {
        VBox container = new VBox();
        container.setMinHeight(Region.USE_PREF_SIZE);
        container.setPrefHeight(Region.USE_COMPUTED_SIZE);

        VBox.setVgrow(container, Priority.NEVER);
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
        super.setSpacing(spacing);
        requestLayout();
    }

    public void setPadding_(Insets padding) {
        super.setPadding(padding);
        requestLayout();
    }

    public void setAlignment_(Pos alignment) {
        super.setAlignment(alignment);
        requestLayout();
    }

    // @Override
    // protected void layoutChildren() {
    // double contentWidth = getWidth() - padding.getLeft() - padding.getRight();
    // double contentHeight = getHeight() - padding.getTop() - padding.getBottom();

    // // Altura total dos filhos
    // double totalHeight = 0;
    // for (Node child : getChildren()) {
    // totalHeight += child.prefHeight(-1);
    // }
    // totalHeight += (getChildren().size() - 1) * spacing;

    // // Offset inicial vertical
    // double yOffset;
    // if (alignment == Pos.BOTTOM_CENTER || alignment == Pos.BOTTOM_LEFT ||
    // alignment == Pos.BOTTOM_RIGHT) {
    // yOffset = contentHeight - totalHeight;
    // } else if (alignment == Pos.CENTER || alignment == Pos.CENTER_LEFT ||
    // alignment == Pos.CENTER_RIGHT) {
    // yOffset = (contentHeight - totalHeight) / 2;
    // } else {
    // yOffset = 0;
    // }
    // yOffset += padding.getTop();

    // // Layout de cada filho
    // for (Node child : getChildren()) {
    // double childWidth;
    // // Respeita fillMaxWidth (maxWidth == Double.MAX_VALUE)
    // if (child.maxWidth(Double.MAX_VALUE) == Double.MAX_VALUE) {
    // childWidth = contentWidth;
    // } else {
    // childWidth = Math.min(contentWidth, child.prefWidth(-1));
    // }

    // double childHeight = child.prefHeight(-1);

    // Insets margin = VBox.getMargin(child);
    // double topMargin = (margin != null) ? margin.getTop() : 0;

    // double xOffset;
    // if (childWidth == contentWidth) {
    // // Preenche toda a largura -> começa do padding esquerdo
    // xOffset = padding.getLeft();
    // } else if (alignment == Pos.TOP_LEFT || alignment == Pos.CENTER_LEFT ||
    // alignment == Pos.BOTTOM_LEFT) {
    // xOffset = padding.getLeft();
    // } else if (alignment == Pos.TOP_RIGHT || alignment == Pos.CENTER_RIGHT ||
    // alignment == Pos.BOTTOM_RIGHT) {
    // xOffset = padding.getLeft() + (contentWidth - childWidth);
    // } else {
    // xOffset = padding.getLeft() + (contentWidth - childWidth) / 2;
    // }

    // yOffset += topMargin; // aplica marginTop
    // child.resizeRelocate(xOffset, yOffset, childWidth, childHeight);
    // yOffset += childHeight + spacing;
    // }
    // }

    @Override
    protected double computePrefHeight(double width) {
        double totalHeight = getPadding().getTop() + getPadding().getBottom();
        for (Node child : getChildren()) {
            totalHeight += child.prefHeight(-1);
        }
        totalHeight += (getChildren().isEmpty() ? 0 : (getChildren().size() - 1) * getSpacing());
        return totalHeight;
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier extends LayoutModifier<Column> {

        public InnerModifier(Column vbox) {
            super(vbox);
        }

        @Override
        public InnerModifier alignment(Pos alignment) {
            node.setAlignment_(alignment);
            return this;
        }

        @Override
        public InnerModifier spacing(double spacing) {
            node.setSpacing_(spacing);
            return this;
        }

        @Override
        public InnerModifier padding(double all) {
            node.setPadding_(new Insets(all));
            return this;
        }

        @Override
        public InnerModifier padding(double top, double right, double bottom, double left) {
            node.setPadding(new Insets(top, right, bottom, left));
            return this;
        }

        @Override
        public InnerModifier marginTop(double margin) {
            // Implementação específica para Column se necessário
            return this;
        }

        @Override
        public InnerModifier fillMaxHeight(boolean enable) {
            if (enable) {
                node.setMaxHeight(Double.MAX_VALUE);
                VBox.setVgrow(node, Priority.ALWAYS);

                // Aplica aos filhos já existentes
                for (Node child : node.getChildren()) {
                    VBox.setVgrow(child, Priority.ALWAYS);
                    child.maxHeight(Double.MAX_VALUE);
                }

                // Observa novos filhos
                node.getChildren().addListener((ListChangeListener<Node>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (Node addedChild : change.getAddedSubList()) {
                                VBox.setVgrow(addedChild, Priority.ALWAYS);
                                addedChild.maxHeight(Double.MAX_VALUE);
                            }
                        }
                    }
                });

            } else {
                node.setMaxHeight(Region.USE_PREF_SIZE);
                VBox.setVgrow(node, Priority.NEVER);
            }

            return this;
        }

        @Override
        public InnerModifier fillMaxWidth(boolean enable) {
            node.setFillWidth(enable);

            if (enable) {
                // Aplica aos filhos já existentes
                for (Node child : node.getChildren()) {
                    VBox.setVgrow(child, Priority.ALWAYS);
                    child.maxWidth(Double.MAX_VALUE);
                }

                // Observa novos filhos
                node.getChildren().addListener((ListChangeListener<Node>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (Node addedChild : change.getAddedSubList()) {
                                VBox.setVgrow(addedChild, Priority.ALWAYS);
                                addedChild.maxWidth(Double.MAX_VALUE);
                            }
                        }
                    }
                });
            }

            return this;
        }

        @Override
        public InnerModifier maxHeight(double maxHeight) {
            node.setMaxHeight(maxHeight);
            return this;
        }

        @Override
        public InnerModifier maxWidth(double maxWidth) {
            node.setMaxWidth(maxWidth);
            return this;
        }

        @Override
        public InnerModifier height(double height) {
            node.setHeight(height);
            node.setPrefHeight(height);
            node.setMaxHeight(height);
            return this;
        }

        @Override
        public InnerModifier width(double width) {
            node.setWidth(width);
            node.setPrefWidth(width);
            node.setMaxWidth(width);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends LayoutStyles<Column> {
            private CornerRadii cornerRadii = CornerRadii.EMPTY; // Armazena o raio dos cantos
            private Paint borderColor = null;

            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles bgColor(Color color) {
                modifier.getNode().setBackground(new Background(
                        new BackgroundFill(color,
                                cornerRadii, null)));
                return this;
            }

            @Override
            public InnerStyles borderRadius(int radiusAll) {
                this.cornerRadii = new CornerRadii(radiusAll);

                // Reaplica o background com cantos arredondados
                BackgroundFill currentFill = modifier.getNode().getBackground() != null
                        ? modifier.getNode().getBackground().getFills().get(0)
                        : new BackgroundFill(Color.TRANSPARENT, cornerRadii, null);

                modifier.getNode().setBackground(new Background(
                        new BackgroundFill(
                                currentFill.getFill(),
                                cornerRadii,
                                null)));

                // Atualiza a borda usando a cor definida, se existir
                if (borderColor != null) {
                    modifier.getNode().setBorder(new Border(
                            new BorderStroke(
                                    borderColor,
                                    BorderStrokeStyle.SOLID,
                                    cornerRadii,
                                    new BorderWidths(1))));
                } else {
                    modifier.getNode().setBorder(Border.EMPTY);
                }

                return this;
            }

            @Override
            public InnerStyles borderColor(Paint color) {
                this.borderColor = color;

                // Aplica a borda imediatamente caso já tenha um radius definido
                modifier.getNode().setBorder(new Border(
                        new BorderStroke(
                                borderColor,
                                BorderStrokeStyle.SOLID,
                                cornerRadii,
                                new BorderWidths(1))));
                return this;
            }
        }

    }

}
