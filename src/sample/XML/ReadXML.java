package sample.XML;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sample.enums.DiceRolling;
import sample.models.Dice;


public class ReadXML {

    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        InputStream xmlInput  = new FileInputStream("file.xml");

        SAXParser saxParser = factory.newSAXParser();
        SaxHandler handler   = new SaxHandler();
        saxParser.parse(xmlInput, handler);

        for(Dice g : handler.dices()){
            System.out.println(g.getValue());
        }
    }
}

//class Gradjanin {
//    String ime;
//    String prezime;
//    String oib;
//    Spol spol;
//    Adresa adresa;
//
//    @Override
//    public String toString() {
//        return "Gradjanin{" + "ime=" + ime + ", prezime=" + prezime + ", oib=" + oib + ", spol=" + spol + ", adresa=" + adresa + '}';
//    }
//}
//
//class Adresa {
//    String ulica;
//    String grad;
//    int postanskiBroj;
//
//    @Override
//    public String toString() {
//        return "Adresa{" + "ulica=" + ulica + ", grad=" + grad + ", postanskiBroj=" + postanskiBroj + '}';
//    }
//}
//
//enum Spol {
//    M,
//    F
//}

class SaxHandler extends DefaultHandler {
    private List<Gradjanin> gradjani = new LinkedList<>();
    private List<Dice> dices = new LinkedList<>();

    private Dice currentDice;

    private Gradjanin trenutniGradjanin;
    private Adresa trenutnaAdresa;
    private String trenutniElement;

    public List<Gradjanin> gradjani() { return gradjani; }

    public List<Dice> dices(){ return dices;}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        trenutniElement = qName;

//        if ("Gradjanin".equals(trenutniElement)) {
//            trenutniGradjanin = new Gradjanin();
//            trenutniGradjanin.spol = Spol.valueOf(attributes.getValue("spol"));
//            return;
//        }
//
//        if ("adresa".equals(trenutniElement)) {
//            trenutnaAdresa = new Adresa();
//            return;
//        }
//
//        if ("grad".equals(trenutniElement)) {
//            trenutnaAdresa.postanskiBroj = Integer.valueOf(attributes.getValue("pbr"));
//            return;
//        }

        if ("Dice".equals(trenutniElement)){
            currentDice = new Dice();
            currentDice.setRoll(false);
            DiceRolling rolling = DiceRolling.valueOf(attributes.getValue("roll"));
            if (rolling == DiceRolling.YES)
            {
                currentDice.setRoll(true);
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        if ("Gradjanin".equals(qName)) {
//            gradjani.add(trenutniGradjanin);
//            trenutniGradjanin = null;
//            return;
//        }
//
//        if ("adresa".equals(qName)) {
//            trenutniGradjanin.adresa = trenutnaAdresa;
//            trenutnaAdresa = null;
//            return;
//        }
//
//        trenutniElement = null;

        if("Dice".equals(qName)){
            dices.add(currentDice);
            currentDice = null;
            return;
        }
        trenutniElement = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space

//        switch(trenutniElement) {
//            case "ime" : trenutniGradjanin.ime = value; break;
//            case "prezime" : trenutniGradjanin.prezime = value; break;
//            case "oib" : trenutniGradjanin.oib = value; break;
//            case "spol" : trenutniGradjanin.spol = Spol.valueOf(value); break;
//
//            case "ulica" : trenutnaAdresa.ulica = value; break;
//            case "grad"  : trenutnaAdresa.grad = value; break;
//        }

        switch(trenutniElement) {
            case "value" : currentDice.setValue(Integer.valueOf(value));  break;
//            case "prezime" : trenutniGradjanin.prezime = value; break;
//            case "oib" : trenutniGradjanin.oib = value; break;
//            case "spol" : trenutniGradjanin.spol = Spol.valueOf(value); break;
//
//            case "ulica" : trenutnaAdresa.ulica = value; break;
//            case "grad"  : trenutnaAdresa.grad = value; break;
        }
    }
}

