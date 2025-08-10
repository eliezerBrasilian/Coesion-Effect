package use_cases.show_card_screen;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import toolkit.declarative_components.Card;
import toolkit.declarative_components.ClickableText;
import toolkit.declarative_components.Column;
import toolkit.declarative_components.Spacer;
import toolkit.declarative_components.Text_;
import toolkit.material_ui.textfield.TextField_;
import toolkit.declarative_components.Label_;
import toolkit.theme.ThemeStyler;

/**
 * Demonstração do sistema de tema global aplicado aos componentes declarativos
 */
public class ShowCardScreen extends Column {

        public ShowCardScreen() {
                super((modifier_) -> {
                        modifier_
                                        .alignment(Pos.TOP_LEFT)
                                        .padding(10);

                        new Card((modifier2) -> {
                                modifier2.padding(5);

                                new Column((modifier3) -> {
                                        modifier3.padding(5);

                                        // Usando estilos tipográficos do tema
                                        new Text_("Word of the Day", modifier -> modifier
                                                        .h3()
                                                        .styles().secondary());

                                        new Spacer();

                                        // Título principal com estilo H1
                                        new Text_("be•nev•o•lent", modifier -> modifier.h1());

                                        new Spacer();

                                        // Subtítulo com estilo H3
                                        new Text_("adjective", modifier -> modifier.h3());

                                        new Spacer();

                                        // Texto do corpo com estilo body
                                        new Text_("well meaning and kindly.\r\n" +
                                                        "\"a benevolent smile\"", modifier -> modifier.body());

                                        new Spacer();

                                        // Link com estilo primário
                                        new ClickableText("Learn More", modifier -> modifier
                                                        .styles().color(Color.web("#1976D2")));

                                        new Spacer();

                                        // Exemplo de Label com tema
                                        new Label_("Example Label", modifier -> modifier
                                                        .h3()
                                                        .styles().color(Color.web("#666666")));

                                        new Spacer();

                                        // Exemplo de TextField com tema
                                        new TextField_("Enter text here", modifier -> modifier
                                                        .applyTheme()
                                                        .marginTop(10));
                                });
                        });
                });
        }
}
