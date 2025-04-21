package my_app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    Stage primaryStage;

    int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        Text text = new Text("Count: " + count);

        Button buttonIncrement = new Button("Increment");
        buttonIncrement.setOnAction(e -> {
            count++;
            text.setText("Count: " + count);
        });

        Button buttonDecrement = new Button("Decrement");
        buttonDecrement.setOnAction(e -> {
            count--;
            text.setText("Count: " + count);
        });

        layout.getChildren().add(text);
        layout.getChildren().add(buttonIncrement);
        layout.getChildren().add(buttonDecrement);

        Scene scene = new Scene(layout, 400, 300);

        this.primaryStage.setScene(scene);

        this.primaryStage.setTitle("Hello World");
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}