package sample;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sample.XML.XmlCreate;
import sample.common.JNDI;
import sample.enums.GameType;
import sample.enums.RollCount;
import sample.models.*;
import sample.Server.DiceRollService;
import sample.serialization.SaveLoad;
import sample.threads.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class Controller {
    //TWO IN ONE ROLL AND CONNECT LOGIC
    boolean firstClick = true;
    @FXML
    private Label lblOnline;
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1100)
    );
    private FadeTransition fadeInTxtArea = new FadeTransition(
            Duration.millis(4000)
    );
    private FadeTransition fadeInCall = new FadeTransition(
            Duration.millis(600)
    );

    @FXML
    Label lblCall;
    //CALL LOGIC
    boolean called = false;
    String callBtnID = "";

    //XML LOGIC
    List<YambPaper> yambPapers = new LinkedList<>();

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

    //TOTAL COUNT LOGIC
    @FXML
    Button totalSingleDown;
    @FXML
    Button totalSingleUp;
    @FXML
    Button totalSingleFree;
    @FXML
    Button totalSingleCall;
    @FXML
    Button totalMinMaxDown;
    @FXML
    Button totalMinMaxUp;
    @FXML
    Button totalMinMaxFree;
    @FXML
    Button totalMinMaxCall;
    @FXML
    Button totalSpecialDown;
    @FXML
    Button totalSpecialUp;
    @FXML
    Button totalSpecialFree;
    @FXML
    Button totalSpecialCall;
    @FXML
    Button grandTotalBtn;


    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TextArea playersTextArea;


    private Game game;
    private Player player;
    private boolean gameStartedPlayerOne = false;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //ROLL SYSTEM
    @FXML
    private Button btnRollDice;
    @FXML
    private ToggleButton diceOne;
    @FXML
    private ToggleButton diceTwo;
    @FXML
    private ToggleButton diceThree;
    @FXML
    private ToggleButton diceFour;
    @FXML
    private ToggleButton diceFive;
    @FXML
    private Label rollCount;

    @FXML
    private Button btnReview;


    //ROLLING DICE
    @FXML
    public void rollDice() {
        if (firstClick){
            connectToServer();
            firstClick = false;
            btnRollDice.setText("Roll Dice ");
            return;
        }
        refresher();

        if (game.getPlayerOneRollCount() == RollCount.THIRD){
            return;
        }
        Dice[] oldRoll = getDice();
        game.getDiceSet().setDices(oldRoll);
        if (game.getPlayerOneRollCount() == RollCount.ZERO){
            game.getDiceSet().setAllDiceToRoll();
            diceOne.setSelected(false);
            diceTwo.setSelected(false);
            diceThree.setSelected(false);
            diceFour.setSelected(false);
            diceFive.setSelected(false);
        }

        if (game.getGameType() == GameType.SINGLEPLAYER || game.getGameType() == GameType.SINGLEPLAYER_LOAD){
            runLocalRoll();

        }
        else if(game.getGameType() == GameType.MULTIPLAYER){
            runServerRoll();
        }

        if (game.getPlayerOneRollCount() == RollCount.ZERO) {
            game.setPlayerOneRollCount(RollCount.FIRST);
        }else if (game.getPlayerOneRollCount() == RollCount.FIRST){
            game.setPlayerOneRollCount(RollCount.SECOND);
        }else if (game.getPlayerOneRollCount() == RollCount.SECOND){
            game.setPlayerOneRollCount(RollCount.THIRD);
        }


        DiceUpdateThreadPlayerOne d = new DiceUpdateThreadPlayerOne(game, diceOne, diceTwo, diceThree, diceFour, diceFive, rollCount, btnRollDice);
        Thread t = new Thread(d);
        t.setDaemon(true);
        Platform.runLater(t);

    }

    //ROLLING DICE ON CLIENT
    private void runLocalRoll() {
        DiceSet diceSet = game.getDiceSet();
        for (Dice dice : diceSet.getDices()) {
            if (dice.isRoll()) {
                int rollValue = (int) (Math.random() * 6) + 1;
                dice.setValue(rollValue);
            }
        }
        game.getDiceSet().setDices(diceSet.getDices());
    }
    //ROLLING DICE ON SERVER
    private void runServerRoll() {
        try {
            File rmiPortFile = JNDI.loadConfig();
            int rmiPort = JNDI.readFileInt(rmiPortFile);
            Registry registry = LocateRegistry.getRegistry(rmiPort);
            DiceRollService stub = (DiceRollService) registry.lookup("diceRollService");

            DiceSet response = stub.rollDice(game.getDiceSet());
            game.getDiceSet().setDices(response.getDices());

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
    //REFRESHING DICE LABELS
    private void refresher() {
        if (!gameStartedPlayerOne) {
            DiceUpdateThreadPlayerOne d = new DiceUpdateThreadPlayerOne(game, diceOne, diceTwo, diceThree, diceFour, diceFive, rollCount, btnRollDice);
            Thread t = new Thread(d);
            t.setDaemon(true);
            Platform.runLater(t);
            gameStartedPlayerOne = true;
        }
    }
    //SETTING VALUES FROM ROLLS ON FIELDS/LABELS
    @FXML
    public void setText(ActionEvent event) {
        if (game.getPlayerOneRollCount() == RollCount.ZERO){return;}
        Button btn = (Button) event.getSource();
        if (!btn.getText().equals("")){return;}
        String clickedBtnId = btn.getId();
        if (called == true){
            if (!(callBtnID.equals(clickedBtnId))){
                return;
            }
        }
        int total = processWritingToYambPaper(clickedBtnId);
        if (total == -1){
            return;
        }
        called = false;


            btn.setText(String.valueOf(total));
            int grandTotal = Integer.valueOf(grandTotalBtn.getText());
            grandTotalBtn.setText(String.valueOf(grandTotal + total));

            game.getPlayer().getYambPaper().setTotal(game.getPlayer().getYambPaper().getTotal()+total);
            game.setPlayerOneRollCount(RollCount.ZERO);
            diceOne.setSelected(false);
            diceTwo.setSelected(false);
            diceThree.setSelected(false);
            diceFour.setSelected(false);
            diceFive.setSelected(false);
            //XML LOGIC
            YambPaper currentPaper = game.getPlayer().getYambPaperCopy();
            //SERIALIZE
            try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("currentPaper.dat"))) {
            outStream.writeObject(currentPaper);
            outStream.close();
            } catch (IOException ex) {
            ex.printStackTrace();
            }

            //DESERIALIZE
            YambPaper newPaper = new YambPaper();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                    "currentPaper.dat"));
            newPaper = (YambPaper) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



            yambPapers.add(newPaper);


        //GETTING TOTAL SCORE
        setTotals();
    }
    //TOTALING SET TEXT
    private void setTotals() {
        //SINGLES
        if (game.getPlayer().getYambPaper().getDown().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getDown().setSingleDiceTypeTotal();
            totalSingleDown.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getSingleDiceTypeTotal()));
        }
        if (game.getPlayer().getYambPaper().getUp().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getUp().setSingleDiceTypeTotal();
            totalSingleUp.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getSingleDiceTypeTotal()));
        }
        if (game.getPlayer().getYambPaper().getFree().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getFree().setSingleDiceTypeTotal();
            totalSingleFree.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getSingleDiceTypeTotal()));
        }
        if (game.getPlayer().getYambPaper().getCall().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getCall().setSingleDiceTypeTotal();
            totalSingleCall.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getSingleDiceTypeTotal()));
        }

        //MIN MAX
        if (game.getPlayer().getYambPaper().getDown().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getDown().setMinMaxCalculation();
            totalMinMaxDown.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getMinMaxCalculation()));
        }
        if (game.getPlayer().getYambPaper().getUp().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getUp().setMinMaxCalculation();
            totalMinMaxUp.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getMinMaxCalculation()));
        }
        if (game.getPlayer().getYambPaper().getFree().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getFree().setMinMaxCalculation();
            totalMinMaxFree.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getMinMaxCalculation()));
        }
        if (game.getPlayer().getYambPaper().getCall().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getCall().setMinMaxCalculation();
            totalMinMaxCall.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getMinMaxCalculation()));
        }

        //SPECIALS
        if (game.getPlayer().getYambPaper().getDown().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getDown().setLowerTotal();
           totalSpecialDown.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getLowerTotal()));
        }
        if (game.getPlayer().getYambPaper().getUp().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getUp().setLowerTotal();
            totalSpecialUp.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getLowerTotal()));
        }
        if (game.getPlayer().getYambPaper().getFree().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getFree().setLowerTotal();
            totalSpecialFree.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getLowerTotal()));
        }
        if (game.getPlayer().getYambPaper().getCall().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getCall().setLowerTotal();
            totalSpecialCall.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getLowerTotal()));
        }

        gameComplete();
    }
    //CHECKING IF CLIENT COMPLETED GAME
    private void gameComplete() {

        if (game.getPlayer().getYambPaper().getDown().isSinglesTypeComplete()){
            game.getPlayer().setGameComplete(true);
            System.out.println("DONE!");
        }
    }
    //FROM setText TO GAME -> PLAYER -> PAPER
    private int processWritingToYambPaper(String btnId) {
        int total = 0;
        switch (btnId){
            //ONES
            case "d1":
                game.getPlayer().getYambPaper().getDown().setOnes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getOnes();
                break;
            case "u1":
                if(game.getPlayer().getYambPaper().getUp().getTwos() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setOnes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getOnes();
                break;
            case "f1":
                game.getPlayer().getYambPaper().getFree().setOnes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getOnes();
                break;
            case "c1":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setOnes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getOnes();
                setLblCallText("");
                break;
                //TWOS
            case "d2":
                if(game.getPlayer().getYambPaper().getDown().getOnes() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setTwos(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getTwos();
                break;
            case "u2":
                if(game.getPlayer().getYambPaper().getUp().getThrees() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setTwos(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getTwos();
                break;
            case "f2":
                game.getPlayer().getYambPaper().getFree().setTwos(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getTwos();
                break;
            case "c2":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setTwos(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getTwos();
                setLblCallText("");
                break;
                //THREES
            case "d3":
                if(game.getPlayer().getYambPaper().getDown().getTwos() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setThrees(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getThrees();
                break;
            case "u3":
                if(game.getPlayer().getYambPaper().getUp().getFours() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setThrees(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getThrees();
                break;
            case "f3":
                game.getPlayer().getYambPaper().getFree().setThrees(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getThrees();
                break;
            case "c3":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setThrees(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getThrees();
                setLblCallText("");
                break;
                //FOURS
            case "d4":
                if(game.getPlayer().getYambPaper().getDown().getThrees() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setFours(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getFours();
                break;
            case "u4":
                if(game.getPlayer().getYambPaper().getUp().getFives() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setFours(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getFours();
                break;
            case "f4":
                game.getPlayer().getYambPaper().getFree().setFours(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getFours();
                break;
            case "c4":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setFours(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getFours();
                setLblCallText("");
                break;
                //FIVES
            case "d5":
                if(game.getPlayer().getYambPaper().getDown().getFours() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setFives(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getFives();
                break;
            case "u5":
                if(game.getPlayer().getYambPaper().getUp().getSixes() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setFives(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getFives();
                break;
            case "f5":
                game.getPlayer().getYambPaper().getFree().setFives(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getFives();
                break;
            case "c5":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setFives(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getFives();
                setLblCallText("");
                break;
                //SIXES
            case "d6":
                if(game.getPlayer().getYambPaper().getDown().getFives() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setSixes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getSixes();
                break;
            case "u6":
                if(game.getPlayer().getYambPaper().getUp().getMax() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setSixes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getSixes();
                break;
            case "f6":
                game.getPlayer().getYambPaper().getFree().setSixes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getSixes();
                break;
            case "c6":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setSixes(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getSixes();
                setLblCallText("");
                break;

                //MAX
            case "dMax":
                if(game.getPlayer().getYambPaper().getDown().getSixes() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setMax(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getMax();
                break;
            case "uMax":
                if(game.getPlayer().getYambPaper().getUp().getMin() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setMax(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getMax();
                break;
            case "fMax":
                game.getPlayer().getYambPaper().getFree().setMax(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getMax();
                break;
            case "cMax":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setMax(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getMax();
                setLblCallText("");
                break;
                //MIN
            case "dMin":
                if(game.getPlayer().getYambPaper().getDown().getMax() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setMin(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getMin();
                break;
            case "uMin":
                if(game.getPlayer().getYambPaper().getUp().getTwoPairs() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setMin(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getMin();
                break;
            case "fMin":
                game.getPlayer().getYambPaper().getFree().setMin(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getMin();
                break;
            case "cMin":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setMin(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getMin();
                setLblCallText("");
                break;

                //PAIRS
            case "dPairs":
                if(game.getPlayer().getYambPaper().getDown().getMin() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getTwoPairs();
                break;
            case "uPairs":
                if(game.getPlayer().getYambPaper().getUp().getStraight() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getTwoPairs();
                break;
            case "fPairs":
                game.getPlayer().getYambPaper().getFree().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getTwoPairs();
                break;
            case "cPairs":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getTwoPairs();
                setLblCallText("");
                break;
                //STRAIGHT
            case "dStraight":
                if(game.getPlayer().getYambPaper().getDown().getTwoPairs() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setStraight(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getStraight();
                break;
            case "uStraight":
                if(game.getPlayer().getYambPaper().getUp().getFull() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setStraight(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getStraight();
                break;
            case "fStraight":
                game.getPlayer().getYambPaper().getFree().setStraight(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getStraight();
                break;
            case "cStraight":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setStraight(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getStraight();
                setLblCallText("");
                break;
                //FULL
            case "dFull":
                if(game.getPlayer().getYambPaper().getDown().getStraight() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setFull(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getFull();
                break;
            case "uFull":
                if(game.getPlayer().getYambPaper().getUp().getPoker() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setFull(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getFull();
                break;
            case "fFull":
                game.getPlayer().getYambPaper().getFree().setFull(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getFull();
                break;
            case "cFull":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setFull(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getFull();
                setLblCallText("");
                break;
                //POKER
            case "dPoker":
                if(game.getPlayer().getYambPaper().getDown().getFull() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setPoker(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getPoker();
                break;
            case "uPoker":
                if(game.getPlayer().getYambPaper().getUp().getYamb() == -1){return -1;}
                game.getPlayer().getYambPaper().getUp().setPoker(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getPoker();
                break;
            case "fPoker":
                game.getPlayer().getYambPaper().getFree().setPoker(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getPoker();
                break;
            case "cPoker":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setPoker(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getPoker();
                setLblCallText("");
                break;
                //YAMB
            case "dYamb":
                if(game.getPlayer().getYambPaper().getDown().getPoker() == -1){return -1;}
                game.getPlayer().getYambPaper().getDown().setYamb(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getDown().getYamb();
                break;
            case "uYamb":
                game.getPlayer().getYambPaper().getUp().setYamb(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getUp().getYamb();
                break;
            case "fYamb":
                game.getPlayer().getYambPaper().getFree().setYamb(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getFree().getYamb();
                break;
            case "cYamb":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayer().getYambPaper().getCall().setYamb(game.getDiceSet().getDices());
                total = game.getPlayer().getYambPaper().getCall().getYamb();
                setLblCallText("");
                break;
        }
        return total;
    }
    //WRITING WHAT IS CALLED IN LABEL
    private void setLblCallText(String text) {
        lblCall.setText(text);
    }
    //HANDLING CALL LOGIC ON BUTTONS
    private int callingProcessor(String btnId) {
        if (!(game.getPlayerOneRollCount() == RollCount.FIRST)){return -1;}
        called = true;
        callBtnID = btnId;
        updateCallLabel(btnId);
        return -1;
    }
    //UPDATE CALL LABEL
    private void updateCallLabel(String btnId) {
        switch (btnId){
            case "c1":
                lblCall.setText("Called ones");
                break;
            case "c2":
                lblCall.setText("Called twos");
                break;
            case "c3":
                lblCall.setText("Called threes");
                break;
            case "c4":
                lblCall.setText("Called fours");
                break;
            case "c5":
                lblCall.setText("Called fives");
                break;
            case "c6":
                lblCall.setText("Called sixes");
                break;
            case "cMax":
                lblCall.setText("Called max");
                break;
            case "cMin":
                lblCall.setText("Called min");
                break;
            case "cPairs":
                lblCall.setText("Called pairs");
                break;
            case "cStraight":
                lblCall.setText("Called straight");
                break;
            case "cFull":
                lblCall.setText("Called full");
                break;
            case "cPoker":
                lblCall.setText("Called poker");
                break;
            case "cYamb":
                lblCall.setText("Called yamb");
                break;
            default:
                lblCall.setText("ERROR");
                break;
        }
    }
    //CHECKING IF CLIENT SHOULD CONNECT TO SERVER AND CONNECTS IF CLIENT SHOULD
    @FXML
    public void connectToServer(){
        btnReview.setDisable(false);
        if (game.getGameType() == GameType.MULTIPLAYER){
            ClientServerScoreUpdate cssu = new ClientServerScoreUpdate(playersTextArea, game.getPlayer());
            Thread t = new Thread(cssu);
            t.setDaemon(true);
            Platform.runLater(t);
            playersTextArea.setVisible(true);
            if (!lblOnline.isVisible()) {
                lblOnline.setVisible(true);
                playersTextArea.setVisible(true);
                fadeIn.playFromStart();
                fadeInTxtArea.playFromStart();
            }
        }
        if (game.getGameType() == GameType.SINGLEPLAYER_LOAD){
            loadGame();
            SaveLoad save = new SaveLoad();
            yambPapers = save.loadReviewData();
        }

    }

    //GETTING CURRENT DICE FROM TOGGLE DICE BUTTONS
    private Dice[] getDice() {
        Dice dice1 = new Dice(Integer.parseInt(diceOne.getText()), diceOne.isSelected(), "prva");
        Dice dice2 = new Dice(Integer.parseInt(diceTwo.getText()), diceTwo.isSelected(), "druga");
        Dice dice3 = new Dice(Integer.parseInt(diceThree.getText()), diceThree.isSelected(), "treća");
        Dice dice4 = new Dice(Integer.parseInt(diceFour.getText()), diceFour.isSelected(), "četvrta");
        Dice dice5 = new Dice(Integer.parseInt(diceFive.getText()), diceFive.isSelected(), "peta");
        return new Dice[]{dice1, dice2, dice3, dice4, dice5};
    }

    public void saveGame(ActionEvent actionEvent) {
        SaveLoad save = new SaveLoad();
        save.saveGame(game);
        save.saveReviewData(yambPapers);
    }

    public void loadGame() {
        //ActionEvent actionEvent (iz loadGame(ActionEvent actionEvent)
        refresher();
        loadToFxml();

    }


    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    //OPENING WINDOW WITH CLASSES LIST
    @FXML
    public void openClassesWindow(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Classes");
        dialog.setHeaderText("Classes Doc Stuff");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("reflectionClasses.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
    }
    //OPENING REVIEW WINDOW
    @FXML
    public void openReviewWindow(ActionEvent actionEvent) throws TransformerException, ParserConfigurationException {
        XmlCreate.writeYambPapersToXml(yambPapers);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Review");
//        dialog.setHeaderText("Moves Review");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("review.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load review dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
    }


    
    
    //LOAD GAME LOGIC
    private void loadToFxml() {
        checkTotals();

        //SINGLEZ

        if (game.getPlayer().getYambPaper().getDown().getOnes() != -1){
            d1.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getOnes()));
        }
        if (game.getPlayer().getYambPaper().getUp().getOnes() != -1){
            u1.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getOnes()));
        }
        if (game.getPlayer().getYambPaper().getFree().getOnes() != -1){
            f1.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getOnes()));
        }
        if (game.getPlayer().getYambPaper().getCall().getOnes() != -1){
            c1.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getOnes()));
        }

        if (game.getPlayer().getYambPaper().getDown().getTwos() != -1){
            d2.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getTwos()));
        }
        if (game.getPlayer().getYambPaper().getUp().getTwos() != -1){
            u2.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getTwos()));
        }
        if (game.getPlayer().getYambPaper().getFree().getTwos() != -1){
            f2.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getTwos()));
        }
        if (game.getPlayer().getYambPaper().getCall().getTwos() != -1){
            c2.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getTwos()));
        }

        if (game.getPlayer().getYambPaper().getDown().getThrees() != -1){
            d3.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getThrees()));
        }
        if (game.getPlayer().getYambPaper().getUp().getThrees() != -1){
            u3.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getThrees()));
        }
        if (game.getPlayer().getYambPaper().getFree().getThrees() != -1){
            f3.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getThrees()));
        }
        if (game.getPlayer().getYambPaper().getCall().getThrees() != -1){
            c3.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getThrees()));
        }

        if (game.getPlayer().getYambPaper().getDown().getFours() != -1){
            d4.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getFours()));
        }
        if (game.getPlayer().getYambPaper().getUp().getFours() != -1){
            u4.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getFours()));
        }
        if (game.getPlayer().getYambPaper().getFree().getFours() != -1){
            f4.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getFours()));
        }
        if (game.getPlayer().getYambPaper().getCall().getFours() != -1){
            c4.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getFours()));
        }

        if (game.getPlayer().getYambPaper().getDown().getFives() != -1){
            d5.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getFives()));
        }
        if (game.getPlayer().getYambPaper().getUp().getFives() != -1){
            u5.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getFives()));
        }
        if (game.getPlayer().getYambPaper().getFree().getFives() != -1){
            f5.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getFives()));
        }
        if (game.getPlayer().getYambPaper().getCall().getFives() != -1){
            c5.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getFives()));
        }

        if (game.getPlayer().getYambPaper().getDown().getSixes() != -1){
            d6.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getSixes()));
        }
        if (game.getPlayer().getYambPaper().getUp().getSixes() != -1){
            u6.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getSixes()));
        }
        if (game.getPlayer().getYambPaper().getFree().getSixes() != -1){
            f6.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getSixes()));
        }
        if (game.getPlayer().getYambPaper().getCall().getSixes() != -1){
            c6.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getSixes()));
        }

        //MAXMIN

        if (game.getPlayer().getYambPaper().getDown().getMax() != -1){
            dMax.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getMax()));
        }
        if (game.getPlayer().getYambPaper().getUp().getMax() != -1){
            uMax.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getMax()));
        }
        if (game.getPlayer().getYambPaper().getFree().getMax() != -1){
            fMax.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getMax()));
        }
        if (game.getPlayer().getYambPaper().getCall().getMax() != -1){
            cMax.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getMax()));
        }

        if (game.getPlayer().getYambPaper().getDown().getMin() != -1){
            dMin.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getMin()));
        }
        if (game.getPlayer().getYambPaper().getUp().getMin() != -1){
            uMin.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getMin()));
        }
        if (game.getPlayer().getYambPaper().getFree().getMin() != -1){
            fMin.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getMin()));
        }
        if (game.getPlayer().getYambPaper().getCall().getMin() != -1){
            cMin.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getMin()));
        }

        //SPECIAL

        if (game.getPlayer().getYambPaper().getDown().getTwoPairs() != -1){
            dPairs.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getTwoPairs()));
        }
        if (game.getPlayer().getYambPaper().getUp().getTwoPairs() != -1){
            uPairs.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getTwoPairs()));
        }
        if (game.getPlayer().getYambPaper().getFree().getTwoPairs() != -1){
            fPairs.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getTwoPairs()));
        }
        if (game.getPlayer().getYambPaper().getCall().getTwoPairs() != -1){
            cPairs.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getTwoPairs()));
        }

        if (game.getPlayer().getYambPaper().getDown().getStraight() != -1){
            dStraight.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getStraight()));
        }
        if (game.getPlayer().getYambPaper().getUp().getStraight() != -1){
            uStraight.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getStraight()));
        }
        if (game.getPlayer().getYambPaper().getFree().getStraight() != -1){
            fStraight.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getStraight()));
        }
        if (game.getPlayer().getYambPaper().getCall().getStraight() != -1){
            cStraight.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getStraight()));
        }

        if (game.getPlayer().getYambPaper().getDown().getFull() != -1){
            dFull.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getFull()));
        }
        if (game.getPlayer().getYambPaper().getUp().getFull() != -1){
            uFull.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getFull()));
        }
        if (game.getPlayer().getYambPaper().getFree().getFull() != -1){
            fFull.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getFull()));
        }
        if (game.getPlayer().getYambPaper().getCall().getFull() != -1){
            cFull.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getFull()));
        }

        if (game.getPlayer().getYambPaper().getDown().getPoker() != -1){
            dPoker.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getPoker()));
        }
        if (game.getPlayer().getYambPaper().getUp().getPoker() != -1){
            uPoker.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getPoker()));
        }
        if (game.getPlayer().getYambPaper().getFree().getPoker() != -1){
            fPoker.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getPoker()));
        }
        if (game.getPlayer().getYambPaper().getCall().getPoker() != -1){
            cPoker.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getPoker()));
        }

        if (game.getPlayer().getYambPaper().getDown().getYamb() != -1){
            dYamb.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getYamb()));
        }
        if (game.getPlayer().getYambPaper().getUp().getYamb() != -1){
            uYamb.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getYamb()));
        }
        if (game.getPlayer().getYambPaper().getFree().getYamb() != -1){
            fYamb.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getYamb()));
        }
        if (game.getPlayer().getYambPaper().getCall().getYamb() != -1){
            cYamb.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getYamb()));
        }



    }
    private void checkTotals() {
        //SINGLES
        if (game.getPlayer().getYambPaper().getDown().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getDown().setSingleDiceTypeTotal();
            totalSingleDown.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getSingleDiceTypeTotal()));
        }
        if (game.getPlayer().getYambPaper().getUp().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getUp().setSingleDiceTypeTotal();
            totalSingleUp.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getSingleDiceTypeTotal()));
        }
        if (game.getPlayer().getYambPaper().getFree().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getFree().setSingleDiceTypeTotal();
            totalSingleFree.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getSingleDiceTypeTotal()));
        }
        if (game.getPlayer().getYambPaper().getCall().isSinglesTypeComplete()){
            game.getPlayer().getYambPaper().getCall().setSingleDiceTypeTotal();
            totalSingleCall.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getSingleDiceTypeTotal()));
        }

        //MIN MAX
        if (game.getPlayer().getYambPaper().getDown().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getDown().setMinMaxCalculation();
            totalMinMaxDown.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getMinMaxCalculation()));
        }
        if (game.getPlayer().getYambPaper().getUp().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getUp().setMinMaxCalculation();
            totalMinMaxUp.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getMinMaxCalculation()));
        }
        if (game.getPlayer().getYambPaper().getFree().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getFree().setMinMaxCalculation();
            totalMinMaxFree.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getMinMaxCalculation()));
        }
        if (game.getPlayer().getYambPaper().getCall().isMinMaxComplete()){
            game.getPlayer().getYambPaper().getCall().setMinMaxCalculation();
            totalMinMaxCall.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getMinMaxCalculation()));
        }

        //SPECIALS
        if (game.getPlayer().getYambPaper().getDown().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getDown().setLowerTotal();
            totalSpecialDown.setText(String.valueOf(game.getPlayer().getYambPaper().getDown().getLowerTotal()));
        }
        if (game.getPlayer().getYambPaper().getUp().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getUp().setLowerTotal();
            totalSpecialUp.setText(String.valueOf(game.getPlayer().getYambPaper().getUp().getLowerTotal()));
        }
        if (game.getPlayer().getYambPaper().getFree().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getFree().setLowerTotal();
            totalSpecialFree.setText(String.valueOf(game.getPlayer().getYambPaper().getFree().getLowerTotal()));
        }
        if (game.getPlayer().getYambPaper().getCall().isLowerTotalComplete()){
            game.getPlayer().getYambPaper().getCall().setLowerTotal();
            totalSpecialCall.setText(String.valueOf(game.getPlayer().getYambPaper().getCall().getLowerTotal()));
        }

        setGrandTotal();
    }
    private void setGrandTotal() {
        grandTotalBtn.setText(String.valueOf(game.getPlayer().getYambPaper().getTotalFromAllLines()));
    }


    @FXML
    private BorderPane imageBorderPane;
    @FXML
    public void initialize(){
        fadeInCall.setNode(lblOnline);
        fadeInCall.setFromValue(0.0);
        fadeInCall.setToValue(1.0);
        fadeInCall.setCycleCount(Timeline.INDEFINITE);
        fadeInCall.setAutoReverse(true);
        fadeInCall.playFromStart();

        fadeIn.setNode(lblCall);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(Timeline.INDEFINITE);
        fadeIn.setAutoReverse(true);

        fadeInTxtArea.setNode(playersTextArea);
        fadeInTxtArea.setFromValue(0.0);
        fadeInTxtArea.setToValue(1.0);
        fadeInTxtArea.setCycleCount(1);
        fadeInTxtArea.setAutoReverse(false);


        Image img = new Image("sample/png/dice.png");
        imageBorderPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(
                        40, 40, true, true, true, false))));
        mainBorderPane.setStyle("-fx-background-color: darkslategray");
    }


}




