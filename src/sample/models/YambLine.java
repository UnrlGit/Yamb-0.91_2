package sample.models;

import sample.enums.DiceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YambLine implements Serializable {
    // upper section
    private int ones = -1;
    private int twos = -1;
    private int threes = -1;
    private int fours = -1;
    private int fives = -1;
    private int sixes = -1;

    //upper section total;
    private int singleDiceTypeTotal = -1;

    private int max = -1;
    private int min = -1;
    private int minMaxCalculation = -1;

    //lower section
    private int twoPairs = -1;
    private int straight = -1;
    private int full = -1;
    private int poker = -1;
    private int yamb = -1;

    private int lowerTotal = -1;


    private int totalScore = -1;

    public YambLine() {
    }


    // SINGLES + CALCULCATION OF TOTAL + CHECKING
    public int getOnes() {
        return ones;
    }
    public void setOnes(Dice[] ones) {
        int totalOnes = 0;
        for (Dice d:ones)
        {
            if (d.getValue() == 1)
            {
                totalOnes++;
            }
        }
        this.ones = totalOnes;
    }

    public int getTwos() {
        return twos; }
    public void setTwos(Dice[] twos) {
        int totalTwos = 0;
        for (Dice d:twos)
        {
            if (d.getValue() == 2)
            {
                totalTwos+=2;
            }
        }
        this.twos = totalTwos;
    }

    public int getThrees() {
        return threes; }
    public void setThrees(Dice[] threes) {
        int totalThrees = 0;
        for (Dice d:threes)
        {
            if (d.getValue() == 3)
            {
                totalThrees+=3;
            }
        }
        this.threes = totalThrees;
    }

    public int getFours() {
        return fours; }
    public void setFours(Dice[] fours) {
        int totalFours = 0;
        for (Dice d:fours)
        {
            if (d.getValue() == 4)
            {
                totalFours+=4;
            }
        }
        this.fours = totalFours;
    }

    public int getFives() {
        return fives; }
    public void setFives(Dice[] fives) {
        int totalFives = 0;
        for (Dice d:fives)
        {
            if (d.getValue() == 5)
            {
                totalFives+=5;
            }
        }
        this.fives = totalFives;
    }

    public int getSixes() {
        return sixes; }
    public void setSixes(Dice[] sixes) {
        int totalSixes = 0;
        for (Dice d:sixes)
        {
            if (d.getValue() == 6)
            {
                totalSixes+=6;
            }
        }
        this.sixes = totalSixes;
    }

    public int getSingleDiceTypeTotal() {
        return singleDiceTypeTotal;
    }
    public void setSingleDiceTypeTotal() {
        int total = ones+twos + threes + fours+fives+sixes;
        if (total >= 60){
            total +=30;
        }
        this.singleDiceTypeTotal = total;
    }

    public boolean isSinglesTypeComplete(){
        if (ones == -1 || twos == -1 || threes == -1 || fours == -1||fives == -1 ||sixes == -1){
            return false;
        }
        return true;
    }



    //MIN MAX LOGIC
    public int getMax() {
        return max;
    }
    public void setMax(Dice[] diceSet) {
        int max = 0;
        for (Dice d:diceSet) {
            max += d.getValue();
        }

        this.max = max;
    }

    public int getMin() {
        return min;
    }
    public void setMin(Dice[] diceSet) {
        int min =0;
        for (Dice d:diceSet) {
            min += d.getValue();
        }
        this.min = min;
    }

    public int getMinMaxCalculation() {
        return minMaxCalculation;
    }
    public void setMinMaxCalculation() {
        if (max-min < 0)
        {
            this.minMaxCalculation = 0;
            return;
        }
        int minMaxCalculate = (max - min)* ones;
        this.minMaxCalculation = minMaxCalculate;
    }
    public boolean isMinMaxComplete(){
        if (min == -1 || max == -1 || ones == -1){
            return false;
        }
        return true;
    }



    // SPECIALS LOGIC
    public int getTwoPairs() {
        return twoPairs;
    }
    public void setTwoPairs(Dice[] diceSet) {
        int countedDice[] = getSortedDice(diceSet);
        int bonus = 10;
        this.twoPairs = bonus;
        int pairsCounter = 0;
        for (int i = 1; i < countedDice.length; i++) {
            if(countedDice[i] == 4 || countedDice[i] == 5){
                this.twoPairs = i*4 + bonus;
                return;
            }
            if(countedDice[i] ==2 || countedDice [i] == 3){
                this.twoPairs += (i*2);
                pairsCounter++;
            }
        }
        if (pairsCounter != 2){this.twoPairs=0;}
    }

    public int getStraight() {
        return straight; }
    public void setStraight(Dice[] diceSet) {
        int countedDice[] = getSortedDice(diceSet);
        if (countedDice[1] == 1 && countedDice[2] == 1 && countedDice[3] == 1
            && countedDice[4] == 1 && countedDice[5] == 1) {
            this.straight = 35;
        }else if(countedDice[2] == 1 && countedDice[3] == 1 && countedDice[4] == 1
                && countedDice[5] == 1 && countedDice[6] == 1 ) {
            this.straight = 45;
        }else{
            this.straight = 0;
        }
    }

    public int getFull() {
        return full; }
    public void setFull(Dice[] diceSet) {
        int countedDice[] = getSortedDice(diceSet);
        System.out.println("DICE LENGTH: " + countedDice.length);
        int bonus = 30;
        boolean hasThreeOfType = false;
        boolean hasTwoOfType = false;
        this.full = 0;
        this.full += bonus;
        System.out.println(this.full + "1");
        for (int i = 1; i < countedDice.length; i++) {
            if(countedDice[i] == 5){
                this.full += (i*5);
                return;
            }
            if (countedDice[i] == 3){
                hasThreeOfType = true;
                this.full = this.full+(3*i);
                System.out.println(i + "<-- 3");
                System.out.println(this.full + " 2FULL");
            }
            if (countedDice[i] == 2){
                hasTwoOfType = true;
                this.full +=(2*i);
                System.out.println(i + "<-- 2");
                System.out.println(this.full + "3 FULL");
            }
        }

        if (hasTwoOfType == false || hasThreeOfType == false){
            this.full = 0;
        }

    }

    public int getPoker() {
        return poker; }
    public void setPoker(Dice[] diceSet) {
        int countedDice[] = getSortedDice(diceSet);
        int bonus = 40;
        this.poker = 0;
        for (int i = 1; i < countedDice.length; i++) {
            if(countedDice[i] == 4 || countedDice[i] == 5){
                this.poker = i*4 + bonus;
            }
        }
    }

    public int getYamb() {
        return yamb; }
    public void setYamb(Dice[] diceSet) {
        int countedDice[] = getSortedDice(diceSet);
        int bonus = 50;
        this.yamb = 0;
        for (int i = 1; i < countedDice.length; i++) {
            if(countedDice[i] == 5){
                this.yamb = i*5 + bonus;
            }
        }

    }

    public int getLowerTotal() {
        return lowerTotal; }
    public void setLowerTotal() {
        if (isLowerTotalComplete())
        this.lowerTotal = this.twoPairs+this.straight+this.full+this.poker + this.yamb;
    }

    public boolean isLowerTotalComplete(){
        if(this.twoPairs == -1 || this.straight == -1 || this.full == -1
                || this.poker == -1 || this.yamb == -1){
            return false;
        }
        return true;
    }


    //TOTAL
    public int getLineTotalScore() {
        int total = 0;
        //SINGLES
        if (ones != -1){total += ones;}
        if (twos != -1){total += twos;}
        if (threes != -1){total += threes;}
        if (fours != -1){total += fours;}
        if (fives != -1){total += fives;}
        if (sixes != -1){total += sixes;}
        //MAX + MIN
        if (max != -1){total += max;}
        if (min != -1){total += min;}
        //SPECIALS
        if (twoPairs != -1){total += twoPairs;}
        if (straight != -1){total += straight;}
        if (full != -1){total += full;}
        if (poker != -1){total += poker;}
        if (yamb != -1){total += yamb;}

        return total;
    }

    public boolean isTotalScoreComplete(){
        if(this.singleDiceTypeTotal == -1 || this.minMaxCalculation == -1 || this.lowerTotal == -1){
            return false;
        }
        return true;
    }

    //number at array place[1] == count number of ones
    private int[] getSortedDice(Dice[] diceSet){
        int diceArray[] = {-1, 0, 0, 0, 0, 0, 0};

        for (Dice d:diceSet) {
            switch (d.getValue()){
                case 1:
                    diceArray[1]++;
                    break;
                case 2:
                    diceArray[2]++;
                    break;
                case 3:
                    diceArray[3]++;
                    break;
                case 4:
                    diceArray[4]++;
                    break;
                case 5:
                    diceArray[5]++;
                    break;
                case 6:
                    diceArray[6]++;
                    break;
                default:
                    System.out.println("ERROR DICE COUNT");
                    break;

            }
        }
        return diceArray;
    }

    //SETTERS FOR XML

    public void setOnes(int ones) {
        this.ones = ones;
    }

    public void setTwos(int twos) {
        this.twos = twos;
    }

    public void setThrees(int threes) {
        this.threes = threes;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public void setFives(int fives) {
        this.fives = fives;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public void setSingleDiceTypeTotal(int singleDiceTypeTotal) {
        this.singleDiceTypeTotal = singleDiceTypeTotal;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMinMaxCalculation(int minMaxCalculation) {
        this.minMaxCalculation = minMaxCalculation;
    }

    public void setTwoPairs(int twoPairs) {
        this.twoPairs = twoPairs;
    }

    public void setStraight(int straight) {
        this.straight = straight;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public void setPoker(int poker) {
        this.poker = poker;
    }

    public void setYamb(int yamb) {
        this.yamb = yamb;
    }

    public void setLowerTotal(int lowerTotal) {
        this.lowerTotal = lowerTotal;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "YambLine{" +
                "ones=" + ones +
                ", twos=" + twos +
                ", threes=" + threes +
                ", fours=" + fours +
                ", fives=" + fives +
                ", sixes=" + sixes +
                ", singleDiceTypeTotal=" + singleDiceTypeTotal +
                ", max=" + max +
                ", min=" + min +
                ", minMaxCalculation=" + minMaxCalculation +
                ", twoPairs=" + twoPairs +
                ", straight=" + straight +
                ", full=" + full +
                ", poker=" + poker +
                ", yamb=" + yamb +
                ", lowerTotal=" + lowerTotal +
                ", totalScore=" + totalScore +
                '}';
    }
}
