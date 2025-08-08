package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import toolkit.Component;
import toolkit.declarative_components.Button_;
import toolkit.declarative_components.Column;
import toolkit.declarative_components.ImageView_;
import toolkit.declarative_components.ImageView_.Shape;
import toolkit.declarative_components.Image_;
import toolkit.declarative_components.Text_;

import java.awt.Desktop;
import java.net.URI;

public class App extends Application {

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        StackPane root = new StackPane();

        root.setBackground(Wallpaper());

        root.getChildren().addAll(OverLayout());

        Scene scene = new Scene(root, 700, 500);
        setup(scene);

        this.primaryStage.show();
    }

    @Component
    Background Wallpaper() {
        return new Image_("/assets/coesion-efffect-bg.jpg").asBackground();
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

        return new ImageView_(new Image_("/assets/coesion-effect-logo.png"), (modifier) -> {
            modifier
                    .width(150)
                    .height(150)
                    .shape(Shape.Rectangle, 20)
                    .animation(anim -> {
                        anim.setFromX(1);
                        anim.setFromY(1);
                        anim.setToX(1.3);
                        anim.setToY(1.3);

                        anim.setAutoReverse(true);
                        anim.setCycleCount(2);
                        anim.setDuration(Duration.seconds(1));
                    });

        });

    }

    @Component
    VBox RightContent() {
        return new Column(() -> {

            new Text_("Coesion Effect", m -> {
                m.fontSize(21)
                        .styles()
                        .color(Color.WHITE)
                        .fontWeightBold();
            });

            new Text_("Build your desktop app rapidly now.", m -> {
                m.styles().color(Color.WHITE);
            });

            new Button_("Join on Github", (btnModifier) -> {
                btnModifier
                        .marginTop(15)
                        .onClick(() -> {
                            try {
                                Desktop.getDesktop()
                                        .browse(new URI("https://github.com/eliezerBrasilian/Coesion-Effect"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .styles()
                        .useCssClass("btn-join");
            });
        });

    }

    void setup(Scene scene) {
        // icon on window
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/assets/app_ico_window_32_32.png")));

        // registering font
        Font.loadFont(
                getClass().getResourceAsStream("/assets/fonts/Montserrat-Regular.ttf"), 16);

        // styles
        scene.getStylesheets().add(
                getClass().getResource("/global_styles.css")
                        .toExternalForm());

        this.primaryStage.setTitle("My Coesion-App");
        this.primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}