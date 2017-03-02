package gent.timdemey.cards;

import gent.timdemey.cards.logic.Card;
import gent.timdemey.cards.logic.Kind;
import gent.timdemey.cards.logic.Pile;
import gent.timdemey.cards.logic.Suit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    
    public static void main(String[] args) {
        Pile pile = new Pile();
        pile.add(new Card(Suit.CLUBS, Kind.ACE));
        pile.add(new Card(Suit.HEARTS, Kind.TEN));
        pile.add(new Card(Suit.DIAMONDS, Kind.TWO));
        
        System.out.println(pile);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button btn = new Button(">> Click <<");
        btn.setOnAction(e -> System.out.println("Hello JavaFX 8"));
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        stage.setScene(new Scene(root));
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setTitle("JavaFX 8 app");
        stage.show();
    }
}
