package sample.serialization;

import sample.enums.GameType;
import sample.models.Game;
import sample.models.Player;
import sample.models.YambPaper;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class SaveLoad {

    public SaveLoad() {
    }

    public void saveGame(Game gamer) {
        try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("yamb.dat"))) {
            outStream.writeObject(gamer);
            outStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Game loadGame() {
        Game gamer = new Game(new Player("SinglePlayer"), GameType.SINGLEPLAYER_LOAD);

        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("yamb.dat"));
            gamer = (Game) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gamer;
    }

    public void saveReviewData(List<YambPaper> yambPapers) {
        try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("yambPapers.dat"))) {
            outStream.writeObject(yambPapers);
            outStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<YambPaper> loadReviewData(){
        List<YambPaper> yambPapers = new LinkedList<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("yambPapers.dat"));
            yambPapers = (List<YambPaper>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            return yambPapers;
        }
        return yambPapers;
    }
}
