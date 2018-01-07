package sample.unused;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sample.Main;

public class StartMenu {
    @FXML
    VBox mainBox = new VBox();

    @FXML
    public void processChoice()  {
        Main.main(null);
    }


}
