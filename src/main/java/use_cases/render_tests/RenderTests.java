package use_cases.render_tests;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import toolkit.declarative_components.Button_;
import toolkit.declarative_components.Column;
import toolkit.declarative_components.Spacer;
import toolkit.declarative_components.Text_;

/**
 * Demonstração de renderização reativa sem verbosidade
 */
public class RenderTests extends Application {

        SimpleBooleanProperty visible = new SimpleBooleanProperty(true);
        SimpleStringProperty nome = new SimpleStringProperty("mauro");

        @Override
        public void start(Stage primaryStage) throws Exception {

                Column root = new Column((self, modifier) -> {

                        modifier.styles().bgColor(Color.BROWN);

                        self.renderIf(visible, () -> new Column(() -> {
                                new Text_("Ola mundo");
                                new Spacer();
                        })).otherwise(() -> new Text_("Ola 2"));

                        new Text_("Ola mundo 2");

                        new Button_("Toogle Ola mundo", () -> {
                                visible.set(!visible.get());
                        });

                        self.renderIf(nome, v -> v.equals("teste"),
                                        () -> new Text_("Nome é teste"));

                        new Button_("Muda Nome", () -> {
                                String current = nome.get();
                                nome.set(current == "teste" ? "mauro" : "teste");
                        });
                });

                primaryStage.setScene(new Scene(root));
                primaryStage.setWidth(400);
                primaryStage.setHeight(250);
                primaryStage.show();

        }

        public static void main(String[] args) {
                launch(args);
        }
}
