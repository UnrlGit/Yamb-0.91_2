package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.enums.GameType;
import sample.models.Dice;
import sample.models.Game;
import sample.models.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game(new Player("SinglePlayer", true), GameType.SINGLEPLAYER);
        GameType gameType = chooseGameType();
        // continueGame = false;
        if (gameType == GameType.SINGLEPLAYER) {
            gameType = continueGame();
        }
        if (gameType == GameType.SINGLEPLAYER_LOAD) {
            game = loadGame();
        } else {
            //  game = new Game(new Player("SinglePlayer", true));
        }
        game.setGameType(gameType);

        if (gameType == GameType.MULTIPLAYER) {
            TextInputDialog dialog = new TextInputDialog("Sherlock");
            dialog.setTitle("Game start");
            dialog.setHeaderText("Choose your nick");
            dialog.setContentText("Enter your nick:");

            Optional<String> playerName = dialog.showAndWait();
            if (playerName.isPresent()) {
                game = new Game(new Player(playerName.get(), true), GameType.MULTIPLAYER);
            } else {
                System.exit(0);
            }

        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setPlayer(game.getPlayer());
        controller.setGame(game);
        primaryStage.setTitle("Yamb: " + game.getPlayer().getNick());
        primaryStage.getIcons().add(new Image("sample/png/unnamed.png"));
        primaryStage.setScene(new Scene(root, 700, 800));
        primaryStage.setOnCloseRequest(t -> Platform.exit());
        primaryStage.show();
    }

    private GameType continueGame() {
       // boolean continueGame = false;
        GameType type = GameType.SINGLEPLAYER;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Continue last game?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");

        ButtonType buttonTypeCancel = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            type = GameType.SINGLEPLAYER_LOAD;
           // continueGame = true;
        } else if (result.get() == buttonTypeTwo) {
           // continueGame = false;
        } else {
            System.exit(0);
        }
        return type;
    }

    public Game loadGame() {
        Game game;

        // kreiranje ObjectInputStream objekta
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                    "yamb.dat"));
            game = (Game) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
          //  e.printStackTrace();
            game = new Game(new Player("SinglePlayer", true), GameType.SINGLEPLAYER_LOAD);
            Dice[] dices = {new Dice(1, true),new Dice(1, true),
                    new Dice(1, true),new Dice(1, true),new Dice(1, true)};
            game.getDiceSet().setDices(dices);
        }
        return game;
    }

    public GameType chooseGameType() {
        GameType gameType = GameType.MULTIPLAYER;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose game type");
        alert.setHeaderText("Do you want to play single player or multi player yamb?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("SinglePlayer");
        ButtonType buttonTypeTwo = new ButtonType("MultiPlayer");
        ButtonType buttonTypeCancel = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);


        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            gameType = GameType.SINGLEPLAYER;
        } else if (result.get() == buttonTypeTwo) {
            gameType = GameType.MULTIPLAYER;
        } else {
            System.exit(0);
        }
        return gameType;
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();

    }
}
