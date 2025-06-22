package my_app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import toolkit.Component;

public class App extends Application {

    Stage primaryStage;

    int count = 0;

    Text text = new Text("Count: " + count);

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // icon on window
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/assets/app_ico_window_32_32.png")));

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        layout.getChildren()
                .addAll(
                        text,
                        ButtonIncrement(),
                        ButtonDecrement());

        Scene scene = new Scene(layout, 400, 300);

        this.primaryStage.setScene(scene);

        this.primaryStage.setTitle("My App");
        this.primaryStage.show();
    }

    @Component
    public Button ButtonIncrement() {
        Button button = new Button("Increment");
        button.setOnAction(e -> {
            count++;
            text.setText("Count: " + count);
        });
        return button;
    }

    @Component
    public Button ButtonDecrement() {
        Button button = new Button("Decrement");
        button.setOnAction(e -> {
            count--;
            text.setText("Count: " + count);
        });
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}