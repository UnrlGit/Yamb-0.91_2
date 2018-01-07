package sample.models;

import java.io.Serializable;

public class YambPaper implements Serializable {
    private YambLine down;
    private YambLine up;
    private YambLine free;
    private YambLine call;
    private boolean writtenSomething;

    public boolean isWrittenSomething() {
        return writtenSomething;
    }

    public void setWrittenSomething(boolean writtenSomething) {
        this.writtenSomething = writtenSomething;
    }

    private int total;


    public YambPaper(YambLine down, YambLine up, YambLine free, YambLine call) {
        this.down = down;
        this.up = up;
        this.free = free;
        this.call = call;
        this.total = 0;
    }

    public YambPaper() {
        this.down = new YambLine();
        this.up = new YambLine();
        this.free = new YambLine();
        this.call = new YambLine();
        this.total = 0;
        this.writtenSomething = true;
    }


    public YambLine getDown() {
        return down;
    }

    public void setDown(YambLine down) {
        this.down = down;
    }

    public YambLine getUp() {
        return up;
    }

    public void setUp(YambLine up) {
        this.up = up;
    }

    public YambLine getFree() {
        return free;
    }

    public void setFree(YambLine free) {
        this.free = free;
    }

    public YambLine getCall() {
        return call;
    }

    public void setCall(YambLine call) {
        this.call = call;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isPaperFull(){
        boolean isItFull = false;
        if (up.isTotalScoreComplete() && down.isTotalScoreComplete() && free.isTotalScoreComplete() && call.isTotalScoreComplete())
        {
            isItFull = true;
        }
        return isItFull;
    }

    public int getTotalFromAllLines(){
        return up.getLineTotalScore() + down.getLineTotalScore()+ free.getLineTotalScore() + call.getLineTotalScore();
    }
}
