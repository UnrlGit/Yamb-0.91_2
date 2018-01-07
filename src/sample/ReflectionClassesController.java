package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.models.ClassReflection;

import java.util.ArrayList;


public class ReflectionClassesController {
    @FXML
    TextArea txtAreaDocument;
    @FXML
    private ComboBox reflectionCB;
    @FXML
    private TextField txtFieldClassSearch;




    public void initialize(){
        ArrayList<String> reflectionItems = new ArrayList<>();
        reflectionItems.add("XmlRead");
        reflectionItems.add("Main");
        reflectionItems.add("Game");
        reflectionItems.add("YambPaper");
        reflectionCB.getItems().setAll(reflectionItems);
        reflectionCB.getSelectionModel().selectFirst();


        reflectionCB.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String currentValue = String.valueOf(newValue);
                processClass(currentValue);
            }
        });
        String firstValue = String.valueOf(reflectionCB.getSelectionModel().getSelectedItem());
        processClass(firstValue);
    }

    private void processClass(String currentValue) {
        ClassReflection classReflection = new ClassReflection();
        switch (currentValue){
            case "Main":
                txtAreaDocument.setText(classReflection.reflectClass("sample.Main"));
                break;
            case "XmlRead":
                txtAreaDocument.setText(classReflection.reflectClass("sample.XML.XmlRead"));
                break;
            case "Game":
                txtAreaDocument.setText(classReflection.reflectClass("sample.models.Game"));
                break;
            case "YambPaper":
                txtAreaDocument.setText(classReflection.reflectClass("sample.models.YambPaper"));
                break;
            default:
                txtAreaDocument.setText("Class not found");
                break;

        }
    }


    public void searchClass(ActionEvent actionEvent) {
        String classSearch = txtFieldClassSearch.getText().trim();
        processClass(classSearch);
    }
}
