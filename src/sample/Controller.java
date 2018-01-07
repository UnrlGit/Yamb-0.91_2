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
import sample.serialization.Save;
import sample.threads.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    private boolean gameStartedPlayerTwo = false;
    ExecutorService executorService = Executors.newFixedThreadPool(4);

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
    }

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

    private void runServerRoll() {
        try {
            File rmiPortFile = JNDI.loadConfig();
            int rmiPort = JNDI.readFileInt(rmiPortFile);
            System.out.println(rmiPort);
            Registry registry = LocateRegistry.getRegistry(rmiPort);
            DiceRollService stub = (DiceRollService) registry.lookup("diceRollService");

            DiceSet response = stub.rollDice(game.getDiceSet());
            game.getDiceSet().setDices(response.getDices());

//            for (Dice d:response.getDices()) {
//                System.out.println(d.getValue());
//            }

//            System.out.println(response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void refresher() {
        if (player.isRollsFirst() && !gameStartedPlayerOne) {
            DiceUpdateThreadPlayerOne d = new DiceUpdateThreadPlayerOne(game, diceOne, diceTwo, diceThree, diceFour, diceFive, rollCount, btnRollDice);
            Thread t = new Thread(d);
            t.setDaemon(true);
            Platform.runLater(t);
//                Platform.runLater(new DiceUpdateThreadPlayerOne(game, diceOne, diceTwo, diceThree, diceFour, diceFive, rollCount, btnRollDice));
            gameStartedPlayerOne = true;
        }
        if (!player.isRollsFirst() && !gameStartedPlayerTwo) {
            DiceUpdateThreadPlayerTwo d = new DiceUpdateThreadPlayerTwo(game, diceOne, diceTwo, diceThree, diceFour, diceFive, rollCount, btnRollDice);
            Thread t = new Thread(d);
            t.setDaemon(true);
            Platform.runLater(t);
//                Platform.runLater(new DiceUpdateThreadPlayerTwo(game, diceOne, diceTwo, diceThree, diceFour, diceFive, rollCount, btnRollDice));
            gameStartedPlayerTwo = true;
        }
    }

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


//            int total = game.getDiceSet().getDiceTotalValue();
            btn.setText(String.valueOf(total));
            int grandTotal = Integer.valueOf(grandTotalBtn.getText());
            grandTotalBtn.setText(String.valueOf(grandTotal + total));

            game.getPlayerOne().getYambPaper().setTotal(game.getPlayerOne().getYambPaper().getTotal()+total);
            game.setPlayerOneRollCount(RollCount.ZERO);
            diceOne.setSelected(false);
            diceTwo.setSelected(false);
            diceThree.setSelected(false);
            diceFour.setSelected(false);
            diceFive.setSelected(false);
            System.out.println(game.getPlayerOne().getYambPaper().getTotal());
            //XML LOGIC
            YambPaper currentPaper = game.getPlayerOne().getYambPaperCopy();
            //SERIALIZE
            try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("currentPaper.dat"))) {
            outStream.writeObject(currentPaper);
            outStream.close();
            System.out.println("uspio ser");
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
            System.out.println("Objekt uspješno pročitan!");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



            yambPapers.add(newPaper);
//        for (YambPaper g :yambPapers){
//            System.out.println("DOWN");
//            System.out.println(g.getDown());
//            System.out.println("UP");
//            System.out.println(g.getUp());
//            System.out.println("FREE");
//            System.out.println(g.getFree());
//            System.out.println("CALL");
//            System.out.println(g.getCall());
//        }
//
//        System.out.println("-----------------------------");
//        System.out.println("-----------------------------");


        //GETTING TOTAL SCORE
        setTotals();
    }

    private void setTotals() {
        //SINGLES
        if (game.getPlayerOne().getYambPaper().getDown().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getDown().setSingleDiceTypeTotal();
            totalSingleDown.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getSingleDiceTypeTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getUp().setSingleDiceTypeTotal();
            totalSingleUp.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getSingleDiceTypeTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getFree().setSingleDiceTypeTotal();
            totalSingleFree.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getSingleDiceTypeTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getCall().setSingleDiceTypeTotal();
            totalSingleCall.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getSingleDiceTypeTotal()));
        }

        //MIN MAX
        if (game.getPlayerOne().getYambPaper().getDown().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getDown().setMinMaxCalculation();
            totalMinMaxDown.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getMinMaxCalculation()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getUp().setMinMaxCalculation();
            totalMinMaxUp.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getMinMaxCalculation()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getFree().setMinMaxCalculation();
            totalMinMaxFree.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getMinMaxCalculation()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getCall().setMinMaxCalculation();
            totalMinMaxCall.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getMinMaxCalculation()));
        }

        //SPECIALS
        if (game.getPlayerOne().getYambPaper().getDown().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getDown().setLowerTotal();
           totalSpecialDown.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getLowerTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getUp().setLowerTotal();
            totalSpecialUp.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getLowerTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getFree().setLowerTotal();
            totalSpecialFree.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getLowerTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getCall().setLowerTotal();
            totalSpecialCall.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getLowerTotal()));
        }

        gameComplete();
    }

    private void gameComplete() {

        if (game.getPlayerOne().getYambPaper().isPaperFull()){
            game.getPlayerOne().setGameComplete(true);
            System.out.println("DONE!");
        }
    }

    private int processWritingToYambPaper(String btnId) {
        int total = 0;
        switch (btnId){
            //ONES
            case "d1":
                game.getPlayerOne().getYambPaper().getDown().setOnes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getOnes();
                break;
            case "u1":
                if(game.getPlayerOne().getYambPaper().getUp().getTwos() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setOnes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getOnes();
                break;
            case "f1":
                game.getPlayerOne().getYambPaper().getFree().setOnes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getOnes();
                break;
            case "c1":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setOnes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getOnes();
                setLblCallText("");
                break;
                //TWOS
            case "d2":
                if(game.getPlayerOne().getYambPaper().getDown().getOnes() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setTwos(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getTwos();
                break;
            case "u2":
                if(game.getPlayerOne().getYambPaper().getUp().getThrees() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setTwos(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getTwos();
                break;
            case "f2":
                game.getPlayerOne().getYambPaper().getFree().setTwos(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getTwos();
                break;
            case "c2":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setTwos(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getTwos();
                setLblCallText("");
                break;
                //THREES
            case "d3":
                if(game.getPlayerOne().getYambPaper().getDown().getTwos() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setThrees(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getThrees();
                break;
            case "u3":
                if(game.getPlayerOne().getYambPaper().getUp().getFours() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setThrees(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getThrees();
                break;
            case "f3":
                game.getPlayerOne().getYambPaper().getFree().setThrees(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getThrees();
                break;
            case "c3":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setThrees(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getThrees();
                setLblCallText("");
                break;
                //FOURS
            case "d4":
                if(game.getPlayerOne().getYambPaper().getDown().getThrees() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setFours(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getFours();
                break;
            case "u4":
                if(game.getPlayerOne().getYambPaper().getUp().getFives() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setFours(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getFours();
                break;
            case "f4":
                game.getPlayerOne().getYambPaper().getFree().setFours(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getFours();
                break;
            case "c4":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setFours(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getFours();
                setLblCallText("");
                break;
                //FIVES
            case "d5":
                if(game.getPlayerOne().getYambPaper().getDown().getFours() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setFives(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getFives();
                break;
            case "u5":
                if(game.getPlayerOne().getYambPaper().getUp().getSixes() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setFives(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getFives();
                break;
            case "f5":
                game.getPlayerOne().getYambPaper().getFree().setFives(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getFives();
                break;
            case "c5":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setFives(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getFives();
                setLblCallText("");
                break;
                //SIXES
            case "d6":
                if(game.getPlayerOne().getYambPaper().getDown().getFives() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setSixes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getSixes();
                break;
            case "u6":
                if(game.getPlayerOne().getYambPaper().getUp().getMax() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setSixes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getSixes();
                break;
            case "f6":
                game.getPlayerOne().getYambPaper().getFree().setSixes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getSixes();
                break;
            case "c6":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setSixes(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getSixes();
                setLblCallText("");
                break;

                //MAX
            case "dMax":
                if(game.getPlayerOne().getYambPaper().getDown().getSixes() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setMax(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getMax();
                break;
            case "uMax":
                if(game.getPlayerOne().getYambPaper().getUp().getMin() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setMax(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getMax();
                break;
            case "fMax":
                game.getPlayerOne().getYambPaper().getFree().setMax(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getMax();
                break;
            case "cMax":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setMax(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getMax();
                setLblCallText("");
                break;
                //MIN
            case "dMin":
                if(game.getPlayerOne().getYambPaper().getDown().getMax() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setMin(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getMin();
                break;
            case "uMin":
                if(game.getPlayerOne().getYambPaper().getUp().getTwoPairs() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setMin(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getMin();
                break;
            case "fMin":
                game.getPlayerOne().getYambPaper().getFree().setMin(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getMin();
                break;
            case "cMin":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setMin(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getMin();
                setLblCallText("");
                break;

                //PAIRS
            case "dPairs":
                if(game.getPlayerOne().getYambPaper().getDown().getMin() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getTwoPairs();
                break;
            case "uPairs":
                if(game.getPlayerOne().getYambPaper().getUp().getStraight() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getTwoPairs();
                break;
            case "fPairs":
                game.getPlayerOne().getYambPaper().getFree().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getTwoPairs();
                break;
            case "cPairs":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setTwoPairs(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getTwoPairs();
                setLblCallText("");
                break;
                //STRAIGHT
            case "dStraight":
                if(game.getPlayerOne().getYambPaper().getDown().getTwoPairs() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setStraight(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getStraight();
                break;
            case "uStraight":
                if(game.getPlayerOne().getYambPaper().getUp().getFull() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setStraight(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getStraight();
                break;
            case "fStraight":
                game.getPlayerOne().getYambPaper().getFree().setStraight(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getStraight();
                break;
            case "cStraight":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setStraight(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getStraight();
                setLblCallText("");
                break;
                //FULL
            case "dFull":
                if(game.getPlayerOne().getYambPaper().getDown().getStraight() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setFull(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getFull();
                break;
            case "uFull":
                if(game.getPlayerOne().getYambPaper().getUp().getPoker() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setFull(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getFull();
                break;
            case "fFull":
                game.getPlayerOne().getYambPaper().getFree().setFull(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getFull();
                break;
            case "cFull":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setFull(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getFull();
                setLblCallText("");
                break;
                //POKER
            case "dPoker":
                if(game.getPlayerOne().getYambPaper().getDown().getFull() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setPoker(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getPoker();
                break;
            case "uPoker":
                if(game.getPlayerOne().getYambPaper().getUp().getYamb() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getUp().setPoker(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getPoker();
                break;
            case "fPoker":
                game.getPlayerOne().getYambPaper().getFree().setPoker(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getPoker();
                break;
            case "cPoker":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setPoker(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getPoker();
                setLblCallText("");
                break;
                //YAMB
            case "dYamb":
                if(game.getPlayerOne().getYambPaper().getDown().getPoker() == -1){return -1;}
                game.getPlayerOne().getYambPaper().getDown().setYamb(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getDown().getYamb();
                break;
            case "uYamb":
                game.getPlayerOne().getYambPaper().getUp().setYamb(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getUp().getYamb();
                break;
            case "fYamb":
                game.getPlayerOne().getYambPaper().getFree().setYamb(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getFree().getYamb();
                break;
            case "cYamb":
                if (called == false){return callingProcessor(btnId);}
                game.getPlayerOne().getYambPaper().getCall().setYamb(game.getDiceSet().getDices());
                total = game.getPlayerOne().getYambPaper().getCall().getYamb();
                setLblCallText("");
                break;
        }
        return total;
    }

    private void setLblCallText(String text) {
        lblCall.setText(text);
    }

    private int callingProcessor(String btnId) {
        if (!(game.getPlayerOneRollCount() == RollCount.FIRST)){return -1;}
        called = true;
        callBtnID = btnId;
        updateCallLabel(btnId);
        return -1;
    }

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

    @FXML
    public void connectToServer(){
        btnReview.setDisable(false);
        if (game.getGameType() == GameType.MULTIPLAYER){
            ClientServerScoreUpdate cssu = new ClientServerScoreUpdate(playersTextArea, game.getPlayerOne());
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
            Save save = new Save();
            yambPapers = save.loadReviewData();
        }

    }


    private Dice[] getDice() {
        Dice dice1 = new Dice(Integer.parseInt(diceOne.getText()), diceOne.isSelected(), "prva");
        Dice dice2 = new Dice(Integer.parseInt(diceTwo.getText()), diceTwo.isSelected(), "druga");
        Dice dice3 = new Dice(Integer.parseInt(diceThree.getText()), diceThree.isSelected(), "treća");
        Dice dice4 = new Dice(Integer.parseInt(diceFour.getText()), diceFour.isSelected(), "četvrta");
        Dice dice5 = new Dice(Integer.parseInt(diceFive.getText()), diceFive.isSelected(), "peta");
        return new Dice[]{dice1, dice2, dice3, dice4, dice5};
    }

    public void saveGame(ActionEvent actionEvent) {
        Save save = new Save();
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

    @FXML
    public void openReviewWindow(ActionEvent actionEvent) throws TransformerException, ParserConfigurationException {
        XmlCreate.writeYambPapersToXml(yambPapers);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Review");
        dialog.setHeaderText("Moves Review");
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


    
    
    //LOAD GAME
    private void loadToFxml() {
        checkTotals();

        //SINGLEZ

        if (game.getPlayerOne().getYambPaper().getDown().getOnes() != -1){
            d1.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getOnes()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getOnes() != -1){
            u1.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getOnes()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getOnes() != -1){
            f1.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getOnes()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getOnes() != -1){
            c1.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getOnes()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getTwos() != -1){
            d2.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getTwos()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getTwos() != -1){
            u2.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getTwos()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getTwos() != -1){
            f2.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getTwos()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getTwos() != -1){
            c2.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getTwos()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getThrees() != -1){
            d3.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getThrees()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getThrees() != -1){
            u3.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getThrees()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getThrees() != -1){
            f3.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getThrees()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getThrees() != -1){
            c3.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getThrees()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getFours() != -1){
            d4.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getFours()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getFours() != -1){
            u4.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getFours()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getFours() != -1){
            f4.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getFours()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getFours() != -1){
            c4.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getFours()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getFives() != -1){
            d5.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getFives()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getFives() != -1){
            u5.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getFives()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getFives() != -1){
            f5.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getFives()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getFives() != -1){
            c5.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getFives()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getSixes() != -1){
            d6.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getSixes()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getSixes() != -1){
            u6.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getSixes()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getSixes() != -1){
            f6.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getSixes()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getSixes() != -1){
            c6.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getSixes()));
        }

        //MAXMIN

        if (game.getPlayerOne().getYambPaper().getDown().getMax() != -1){
            dMax.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getMax()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getMax() != -1){
            uMax.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getMax()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getMax() != -1){
            fMax.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getMax()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getMax() != -1){
            cMax.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getMax()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getMin() != -1){
            dMin.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getMin()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getMin() != -1){
            uMin.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getMin()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getMin() != -1){
            fMin.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getMin()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getMin() != -1){
            cMin.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getMin()));
        }

        //SPECIAL

        if (game.getPlayerOne().getYambPaper().getDown().getTwoPairs() != -1){
            dPairs.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getTwoPairs()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getTwoPairs() != -1){
            uPairs.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getTwoPairs()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getTwoPairs() != -1){
            fPairs.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getTwoPairs()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getTwoPairs() != -1){
            cPairs.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getTwoPairs()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getStraight() != -1){
            dStraight.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getStraight()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getStraight() != -1){
            uStraight.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getStraight()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getStraight() != -1){
            fStraight.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getStraight()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getStraight() != -1){
            cStraight.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getStraight()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getFull() != -1){
            dFull.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getFull()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getFull() != -1){
            uFull.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getFull()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getFull() != -1){
            fFull.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getFull()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getFull() != -1){
            cFull.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getFull()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getPoker() != -1){
            dPoker.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getPoker()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getPoker() != -1){
            uPoker.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getPoker()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getPoker() != -1){
            fPoker.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getPoker()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getPoker() != -1){
            cPoker.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getPoker()));
        }

        if (game.getPlayerOne().getYambPaper().getDown().getYamb() != -1){
            dYamb.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getYamb()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().getYamb() != -1){
            uYamb.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getYamb()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().getYamb() != -1){
            fYamb.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getYamb()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().getYamb() != -1){
            cYamb.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getYamb()));
        }



    }

    private void checkTotals() {
        //SINGLES
        if (game.getPlayerOne().getYambPaper().getDown().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getDown().setSingleDiceTypeTotal();
            totalSingleDown.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getSingleDiceTypeTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getUp().setSingleDiceTypeTotal();
            totalSingleUp.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getSingleDiceTypeTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getFree().setSingleDiceTypeTotal();
            totalSingleFree.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getSingleDiceTypeTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().isSinglesTypeComplete()){
            game.getPlayerOne().getYambPaper().getCall().setSingleDiceTypeTotal();
            totalSingleCall.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getSingleDiceTypeTotal()));
        }

        //MIN MAX
        if (game.getPlayerOne().getYambPaper().getDown().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getDown().setMinMaxCalculation();
            totalMinMaxDown.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getMinMaxCalculation()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getUp().setMinMaxCalculation();
            totalMinMaxUp.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getMinMaxCalculation()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getFree().setMinMaxCalculation();
            totalMinMaxFree.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getMinMaxCalculation()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().isMinMaxComplete()){
            game.getPlayerOne().getYambPaper().getCall().setMinMaxCalculation();
            totalMinMaxCall.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getMinMaxCalculation()));
        }

        //SPECIALS
        if (game.getPlayerOne().getYambPaper().getDown().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getDown().setLowerTotal();
            totalSpecialDown.setText(String.valueOf(game.getPlayerOne().getYambPaper().getDown().getLowerTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getUp().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getUp().setLowerTotal();
            totalSpecialUp.setText(String.valueOf(game.getPlayerOne().getYambPaper().getUp().getLowerTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getFree().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getFree().setLowerTotal();
            totalSpecialFree.setText(String.valueOf(game.getPlayerOne().getYambPaper().getFree().getLowerTotal()));
        }
        if (game.getPlayerOne().getYambPaper().getCall().isLowerTotalComplete()){
            game.getPlayerOne().getYambPaper().getCall().setLowerTotal();
            totalSpecialCall.setText(String.valueOf(game.getPlayerOne().getYambPaper().getCall().getLowerTotal()));
        }

        setGrandTotal();
    }

    private void setGrandTotal() {
        grandTotalBtn.setText(String.valueOf(game.getPlayerOne().getYambPaper().getTotalFromAllLines()));
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
//        mainBorderPane.setStyle("-fx-background-color: #87a9be");
        mainBorderPane.setStyle("-fx-background-color: darkslategray");
    }
}




