package use_cases.show_card_screen;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import toolkit.declarative_components.Card;
import toolkit.declarative_components.ClickableText;
import toolkit.declarative_components.Column;
import toolkit.declarative_components.Spacer;
import toolkit.declarative_components.Text_;

/**
 * Here we creating a basic Card component inside a Column container
 */
public class ShowCardScreen extends Column {

    public ShowCardScreen() {
        super((modifier_) -> {
            modifier_
                    .alignment(Pos.TOP_LEFT)
                    .padding(10);

            new Card((modifier2) -> {
                modifier2
                        .padding(5);
                new Column((modifier3) -> {
                    modifier3
                            .padding(5);

                    new Text_("Word of the Day", modifier -> modifier.fontSize(15)
                            .styles().color(Color.GRAY));
                    new Spacer();
                    new Text_("be•nev•o•lent", modifier -> modifier.fontSize(17));

                    new Spacer();
                    new Text_("adjective");
                    new Spacer();

                    new Text_("well meaning and kindly.\r\n" +
                            "\"a benevolent smile\"", modifier -> modifier.fontSize(14));

                    new ClickableText("Learn More");
                });

            });
        });
    }
}
