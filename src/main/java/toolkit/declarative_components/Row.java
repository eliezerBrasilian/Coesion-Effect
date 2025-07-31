package toolkit.declarative_components;

import javafx.scene.layout.HBox;

public class Row extends HBox {
    public Row(Runnable build) {
        FXNodeContext.push(this);
        build.run();
        FXNodeContext.pop();
        FXNodeContext.add(this);
    }

    public Row() {
        FXNodeContext.add(this);
    }
}