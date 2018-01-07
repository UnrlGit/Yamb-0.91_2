package sample.unused;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class test  extends Application{

        public static void main(String[] args) {
        launch(args);
    }

        @Override
        public void start(Stage primaryStage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startMenu.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("Yamb");
        primaryStage.getIcons().add(new Image("sample/png/unnamed.png"));
        primaryStage.setScene(new Scene(root, 300, 450));
        primaryStage.setOnCloseRequest(t -> Platform.exit());
        primaryStage.show();
    }



}
