package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.XML.XmlRead;
import sample.models.YambPaper;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;

public class ReviewController {
    //FXML BUTTONS
    @FXML
    private Button d1;
    @FXML
    private Button u1;
    @FXML
    private Button f1;
    @FXML
    private Button c1;
    @FXML
    private Button d2;
    @FXML
    private Button u2;
    @FXML
    private Button f2;
    @FXML
    private Button c2;
    @FXML
    private Button d3;
    @FXML
    private Button u3;
    @FXML
    private Button f3;
    @FXML
    private Button c3;
    @FXML
    private Button d4;
    @FXML
    private Button u4;
    @FXML
    private Button f4;
    @FXML
    private Button c4;
    @FXML
    private Button d5;
    @FXML
    private Button u5;
    @FXML
    private Button f5;
    @FXML
    private Button c5;
    @FXML
    private Button d6;
    @FXML
    private Button u6;
    @FXML
    private Button f6;
    @FXML
    private Button c6;
    @FXML
    private Button dMax;
    @FXML
    private Button uMax;
    @FXML
    private Button fMax;
    @FXML
    private Button cMax;
    @FXML
    private Button dMin;
    @FXML
    private Button uMin;
    @FXML
    private Button fMin;
    @FXML
    private Button cMin;
    @FXML
    private Button dPairs;
    @FXML
    private Button uPairs;
    @FXML
    private Button fPairs;
    @FXML
    private Button cPairs;
    @FXML
    private Button dStraight;
    @FXML
    private Button uStraight;
    @FXML
    private Button fStraight;
    @FXML
    private Button cStraight;
    @FXML
    private Button dFull;
    @FXML
    private Button uFull;
    @FXML
    private Button fFull;
    @FXML
    private Button cFull;
    @FXML
    private Button dPoker;
    @FXML
    private Button uPoker;
    @FXML
    private Button fPoker;
    @FXML
    private Button cPoker;
    @FXML
    private Button dYamb;
    @FXML
    private Button uYamb;
    @FXML
    private Button fYamb;
    @FXML
    private Button cYamb;

    //TOTALS
    @FXML
    private Button totalSingleDown;
    @FXML
    private Button totalSingleUp;
    @FXML
    private Button totalSingleFree;
    @FXML
    private Button totalSingleCall;
    @FXML
    private Button totalMinMaxDown;
    @FXML
    private Button totalMinMaxUp;
    @FXML
    private Button totalMinMaxFree;
    @FXML
    private Button totalMinMaxCall;
    @FXML
    private Button totalSpecialDown;
    @FXML
    private Button totalSpecialUp;
    @FXML
    private Button totalSpecialFree;
    @FXML
    private Button totalSpecialCall;
    @FXML
    private Button grandTotalBtn;

    //start + next
    @FXML
    private Button btnStart;
    @FXML
    private Button btnNext;

    @FXML
    GridPane grid;
    LinkedList<YambPaper> yambPapers;
    YambPaper current;


    @FXML
    public void setText(){}

    @FXML
    public void start() throws IOException, SAXException, ParserConfigurationException {
        yambPapers = (LinkedList<YambPaper>) XmlRead.getYambPaperXml();

        if (yambPapers.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No moves to review");

            alert.showAndWait();
            Stage stage = (Stage) btnStart.getScene().getWindow();
            stage.close();
        }else {
            btnNext.setDisable(false);
            btnStart.setDisable(true);
            current = yambPapers.getFirst();
            loadToFxml();
            setGrandTotal();
        }
    }




    @FXML
    public void next(){
        clearButtons();
        if(yambPapers.size()>1) {
            yambPapers.removeFirst();
        }
        if (yambPapers.size() > 0){
            current = yambPapers.getFirst();
            loadToFxml();
            checkTotals();
        }
    }

    private void loadToFxml() {
        //SINGLEZ

        if (current.getDown().getOnes() != -1){
            d1.setText(String.valueOf(current.getDown().getOnes()));
        }
        if (current.getUp().getOnes() != -1){
            u1.setText(String.valueOf(current.getUp().getOnes()));
        }
        if (current.getFree().getOnes() != -1){
            f1.setText(String.valueOf(current.getFree().getOnes()));
        }
        if (current.getCall().getOnes() != -1){
            c1.setText(String.valueOf(current.getCall().getOnes()));
        }

        if (current.getDown().getTwos() != -1){
            d2.setText(String.valueOf(current.getDown().getTwos()));
        }
        if (current.getUp().getTwos() != -1){
            u2.setText(String.valueOf(current.getUp().getTwos()));
        }
        if (current.getFree().getTwos() != -1){
            f2.setText(String.valueOf(current.getFree().getTwos()));
        }
        if (current.getCall().getTwos() != -1){
            c2.setText(String.valueOf(current.getCall().getTwos()));
        }

        if (current.getDown().getThrees() != -1){
            d3.setText(String.valueOf(current.getDown().getThrees()));
        }
        if (current.getUp().getThrees() != -1){
            u3.setText(String.valueOf(current.getUp().getThrees()));
        }
        if (current.getFree().getThrees() != -1){
            f3.setText(String.valueOf(current.getFree().getThrees()));
        }
        if (current.getCall().getThrees() != -1){
            c3.setText(String.valueOf(current.getCall().getThrees()));
        }

        if (current.getDown().getFours() != -1){
            d4.setText(String.valueOf(current.getDown().getFours()));
        }
        if (current.getUp().getFours() != -1){
            u4.setText(String.valueOf(current.getUp().getFours()));
        }
        if (current.getFree().getFours() != -1){
            f4.setText(String.valueOf(current.getFree().getFours()));
        }
        if (current.getCall().getFours() != -1){
            c4.setText(String.valueOf(current.getCall().getFours()));
        }

        if (current.getDown().getFives() != -1){
            d5.setText(String.valueOf(current.getDown().getFives()));
        }
        if (current.getUp().getFives() != -1){
            u5.setText(String.valueOf(current.getUp().getFives()));
        }
        if (current.getFree().getFives() != -1){
            f5.setText(String.valueOf(current.getFree().getFives()));
        }
        if (current.getCall().getFives() != -1){
            c5.setText(String.valueOf(current.getCall().getFives()));
        }

        if (current.getDown().getSixes() != -1){
            d6.setText(String.valueOf(current.getDown().getSixes()));
        }
        if (current.getUp().getSixes() != -1){
            u6.setText(String.valueOf(current.getUp().getSixes()));
        }
        if (current.getFree().getSixes() != -1){
            f6.setText(String.valueOf(current.getFree().getSixes()));
        }
        if (current.getCall().getSixes() != -1){
            c6.setText(String.valueOf(current.getCall().getSixes()));
        }

        //MAXMIN

        if (current.getDown().getMax() != -1){
            dMax.setText(String.valueOf(current.getDown().getMax()));
        }
        if (current.getUp().getMax() != -1){
            uMax.setText(String.valueOf(current.getUp().getMax()));
        }
        if (current.getFree().getMax() != -1){
            fMax.setText(String.valueOf(current.getFree().getMax()));
        }
        if (current.getCall().getMax() != -1){
            cMax.setText(String.valueOf(current.getCall().getMax()));
        }

        if (current.getDown().getMin() != -1){
            dMin.setText(String.valueOf(current.getDown().getMin()));
        }
        if (current.getUp().getMin() != -1){
            uMin.setText(String.valueOf(current.getUp().getMin()));
        }
        if (current.getFree().getMin() != -1){
            fMin.setText(String.valueOf(current.getFree().getMin()));
        }
        if (current.getCall().getMin() != -1){
            cMin.setText(String.valueOf(current.getCall().getMin()));
        }

        //SPECIAL

        if (current.getDown().getTwoPairs() != -1){
            dPairs.setText(String.valueOf(current.getDown().getTwoPairs()));
        }
        if (current.getUp().getTwoPairs() != -1){
            uPairs.setText(String.valueOf(current.getUp().getTwoPairs()));
        }
        if (current.getFree().getTwoPairs() != -1){
            fPairs.setText(String.valueOf(current.getFree().getTwoPairs()));
        }
        if (current.getCall().getTwoPairs() != -1){
            cPairs.setText(String.valueOf(current.getCall().getTwoPairs()));
        }

        if (current.getDown().getStraight() != -1){
            dStraight.setText(String.valueOf(current.getDown().getStraight()));
        }
        if (current.getUp().getStraight() != -1){
            uStraight.setText(String.valueOf(current.getUp().getStraight()));
        }
        if (current.getFree().getStraight() != -1){
            fStraight.setText(String.valueOf(current.getFree().getStraight()));
        }
        if (current.getCall().getStraight() != -1){
            cStraight.setText(String.valueOf(current.getCall().getStraight()));
        }

        if (current.getDown().getFull() != -1){
            dFull.setText(String.valueOf(current.getDown().getFull()));
        }
        if (current.getUp().getFull() != -1){
            uFull.setText(String.valueOf(current.getUp().getFull()));
        }
        if (current.getFree().getFull() != -1){
            fFull.setText(String.valueOf(current.getFree().getFull()));
        }
        if (current.getCall().getFull() != -1){
            cFull.setText(String.valueOf(current.getCall().getFull()));
        }

        if (current.getDown().getPoker() != -1){
            dPoker.setText(String.valueOf(current.getDown().getPoker()));
        }
        if (current.getUp().getPoker() != -1){
            uPoker.setText(String.valueOf(current.getUp().getPoker()));
        }
        if (current.getFree().getPoker() != -1){
            fPoker.setText(String.valueOf(current.getFree().getPoker()));
        }
        if (current.getCall().getPoker() != -1){
            cPoker.setText(String.valueOf(current.getCall().getPoker()));
        }

        if (current.getDown().getYamb() != -1){
            dYamb.setText(String.valueOf(current.getDown().getYamb()));
        }
        if (current.getUp().getYamb() != -1){
            uYamb.setText(String.valueOf(current.getUp().getYamb()));
        }
        if (current.getFree().getYamb() != -1){
            fYamb.setText(String.valueOf(current.getFree().getYamb()));
        }
        if (current.getCall().getYamb() != -1){
            cYamb.setText(String.valueOf(current.getCall().getYamb()));
        }



    }

    private void checkTotals() {
        //SINGLES
        if (current.getDown().isSinglesTypeComplete()){
            current.getDown().setSingleDiceTypeTotal();
            totalSingleDown.setText(String.valueOf(current.getDown().getSingleDiceTypeTotal()));
        }
        if (current.getUp().isSinglesTypeComplete()){
            current.getUp().setSingleDiceTypeTotal();
            totalSingleUp.setText(String.valueOf(current.getUp().getSingleDiceTypeTotal()));
        }
        if (current.getFree().isSinglesTypeComplete()){
            current.getFree().setSingleDiceTypeTotal();
            totalSingleFree.setText(String.valueOf(current.getFree().getSingleDiceTypeTotal()));
        }
        if (current.getCall().isSinglesTypeComplete()){
            current.getCall().setSingleDiceTypeTotal();
            totalSingleCall.setText(String.valueOf(current.getCall().getSingleDiceTypeTotal()));
        }

        //MIN MAX
        if (current.getDown().isMinMaxComplete()){
            current.getDown().setMinMaxCalculation();
            totalMinMaxDown.setText(String.valueOf(current.getDown().getMinMaxCalculation()));
        }
        if (current.getUp().isMinMaxComplete()){
            current.getUp().setMinMaxCalculation();
            totalMinMaxUp.setText(String.valueOf(current.getUp().getMinMaxCalculation()));
        }
        if (current.getFree().isMinMaxComplete()){
            current.getFree().setMinMaxCalculation();
            totalMinMaxFree.setText(String.valueOf(current.getFree().getMinMaxCalculation()));
        }
        if (current.getCall().isMinMaxComplete()){
            current.getCall().setMinMaxCalculation();
            totalMinMaxCall.setText(String.valueOf(current.getCall().getMinMaxCalculation()));
        }

        //SPECIALS
        if (current.getDown().isLowerTotalComplete()){
            current.getDown().setLowerTotal();
            totalSpecialDown.setText(String.valueOf(current.getDown().getLowerTotal()));
        }
        if (current.getUp().isLowerTotalComplete()){
            current.getUp().setLowerTotal();
            totalSpecialUp.setText(String.valueOf(current.getUp().getLowerTotal()));
        }
        if (current.getFree().isLowerTotalComplete()){
            current.getFree().setLowerTotal();
            totalSpecialFree.setText(String.valueOf(current.getFree().getLowerTotal()));
        }
        if (current.getCall().isLowerTotalComplete()){
            current.getCall().setLowerTotal();
            totalSpecialCall.setText(String.valueOf(current.getCall().getLowerTotal()));
        }

        setGrandTotal();
    }

    private void setGrandTotal() {
        grandTotalBtn.setText(String.valueOf(current.getTotalFromAllLines()));
    }

    private void clearButtons() {
        //SINGLEZ
            d1.setText("");
            u1.setText("");
            f1.setText("");
            c1.setText("");

            d2.setText("");
            u2.setText("");
            f2.setText("");
            c2.setText("");

            d3.setText("");
            u3.setText("");
            f3.setText("");
            c3.setText("");

            d4.setText("");
            u4.setText("");
            f4.setText("");
            c4.setText("");

            d5.setText("");
            u5.setText("");
            f5.setText("");
            c5.setText("");

            d6.setText("");
            u6.setText("");
            f6.setText("");
            c6.setText("");

        //MAXMIN
            dMax.setText("");
            uMax.setText("");
            fMax.setText("");
            cMax.setText("");

            dMin.setText("");
            uMin.setText("");
            fMin.setText("");
            cMin.setText("");

        //SPECIAL
            dPairs.setText("");
            uPairs.setText("");
            fPairs.setText("");
            cPairs.setText("");

            dStraight.setText("");
            uStraight.setText("");
            fStraight.setText("");
            cStraight.setText("");

            dFull.setText("");
            uFull.setText("");
            fFull.setText("");
            cFull.setText("");

            dPoker.setText("");
            uPoker.setText("");
            fPoker.setText("");
            cPoker.setText("");

            dYamb.setText("");
            uYamb.setText("");
            fYamb.setText("");
            cYamb.setText("");
    }


    public void initialize(){
    }
}
