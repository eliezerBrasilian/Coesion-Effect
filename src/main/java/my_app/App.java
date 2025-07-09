package my_app;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import toolkit.Component;
import java.awt.Desktop;
import java.net.URI;

public class App extends Application {

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        StackPane root = new StackPane();

        root.setBackground(new Background(Wallpaper()));

        root.getChildren().addAll(OverLayout());

        Scene scene = new Scene(root, 700, 500);
        setup(scene);

        this.primaryStage.show();
    }

    @Component
    BackgroundImage Wallpaper() {
        Image bgImage = new Image(getClass().getResourceAsStream("/assets/coesion-efffect-bg.jpg"));

        return new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
    }

    @Component
    AnchorPane OverLayout() {

        ImageView logo = Logo();
        VBox right = RightContent();
        AnchorPane container = new AnchorPane(logo, right);

        AnchorPane.setTopAnchor(logo, 20.0);
        AnchorPane.setLeftAnchor(logo, 20.0);

        AnchorPane.setTopAnchor(right, 20.0);
        AnchorPane.setRightAnchor(right, 20.0);
        return container;
    }

    @Component
    ImageView Logo() {
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/assets/coesion-effect-logo.png")));
        logo.setFitWidth(150);
        logo.setFitHeight(150);

        ScaleTransition zoom = new ScaleTransition(Duration.seconds(1), logo);

        zoom.setFromX(1);
        zoom.setFromY(1);
        zoom.setToX(1.3);
        zoom.setToY(1.3);

        zoom.setAutoReverse(true);
        zoom.setCycleCount(2);
        zoom.play();

        Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        logo.setClip(clip);
        return logo;
    }

    @Component
    VBox RightContent() {
        VBox container = new VBox();
        container.setSpacing(5);
        Text title = new Text("Coesion Effect");
        title.getStyleClass().add("title");
        Text description = new Text("Build your desktop app rapidly now.");
        description.getStyleClass().add("description");

        Button btnJoinTelegram = new Button("Join on Telegram");
        btnJoinTelegram.getStyleClass().add("btn-join-telegram");
        btnJoinTelegram.setOnAction(ev -> {
            try {
                Desktop.getDesktop().browse(new URI("https://t.me/coesion_effect_community"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox.setMargin(btnJoinTelegram, new Insets(15, 0, 0, 0));

        container.getChildren().addAll(
                title,
                description, btnJoinTelegram);
        return container;
    }

    void setup(Scene scene) {
        // icon on window
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/assets/app_ico_window_32_32.png")));

        // registering font
        Font.loadFont(
                getClass().getResourceAsStream("/assets/fonts/Montserrat-Regular.ttf"), 14);

        // styles
        scene.getStylesheets().add(
                getClass().getResource("./styles.css")
                        .toExternalForm());

        this.primaryStage.setTitle("My Coesion-App");
        this.primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}