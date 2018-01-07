package sample.unused;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sample.enums.DiceRolling;
import sample.models.Dice;

enum Spol {
    M,
    F
}

public class CreateXML {
    public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {

        Document xmlDokument = kreirajDokument();

        spremi(xmlDokument, "file.xml");
    }

    private static Document kreirajDokument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        // korjenski element <Dices>
        Element dices = doc.createElement("Dices");

        // postavi gradjane kao glavni element
        doc.appendChild(dices);

        List<Dice> listaKocaka = dohvatiKocke();

        for (Dice dice : listaKocaka) {
            Element diceElement = napraviNoviDiceElement(doc, dice);
            dices.appendChild(diceElement);
        }

        return doc;
    }

    private static Element napraviNoviDiceElement(Document doc, Dice g) {
        Element dice = doc.createElement("Dice");

//        gradjanin.setAttribute("spol", g.spol.name());
        DiceRolling rolling = DiceRolling.NO;
        if (g.isRoll()){
            rolling = DiceRolling.YES;
        }
        dice.setAttribute("roll", rolling.toString());

        Element value = doc.createElement("value");
        value.appendChild(doc.createTextNode(String.valueOf(g.getValue())));
        dice.appendChild(value);

//        Element prezime = doc.createElement("prezime");
//        prezime.appendChild(doc.createTextNode(g.prezime));
//        gradjanin.appendChild(prezime);
//
//        Element ime = doc.createElement("ime");
//        ime.appendChild(doc.createTextNode(g.ime));
//        gradjanin.appendChild(ime);
//
//        // adresa: ulica, grad(pbr=...)
//        Element adresa = doc.createElement("adresa");
//        Element ulica = doc.createElement("ulica");
//        ulica.appendChild(doc.createTextNode(g.adresa.ulica));
//        Element grad = doc.createElement("grad");
//        grad.appendChild(doc.createTextNode(g.adresa.grad));
//        grad.setAttribute("pbr", String.valueOf(g.adresa.postanskiBroj));
//        adresa.appendChild(ulica);
//        adresa.appendChild(grad);
//        gradjanin.appendChild(adresa);

//        return gradjanin;

        return dice;
    }

    private static void spremi(Document doc, String nazivDatoteke) throws TransformerConfigurationException, TransformerException {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("file.xml"));

        transformer.transform(source, result);
        transformer.transform(source, new StreamResult(System.out));
    }


    private static List<Dice> dohvatiKocke() {
        List<Dice> dices = new LinkedList<>();
        dices.add(new Dice(5, false));
        dices.add(new Dice(5, false));
        dices.add(new Dice(5, false));
        dices.add(new Dice(5, false));
        dices.add(new Dice(5, false));
        return dices;

//        List<Gradjanin> gradjani = new LinkedList<>();
//        gradjani.add(new Gradjanin("Who", "Wat", "123456789", Spol.M, new Adresa("Nova ulica", "Zagreb", 10000)));
//        gradjani.add(new Gradjanin("Ana", "Anic", "22222222", Spol.F, new Adresa("Stara ulica", "Zagreb", 10100)));
//        return gradjani;
    }
}

class Gradjanin {

    String ime;
    String prezime;
    String oib;
    Spol spol;
    Adresa adresa;

    public Gradjanin(String ime, String prezime, String oib, Spol spol, Adresa adresa) {
        this.ime = ime;
        this.prezime = prezime;
        this.oib = oib;
        this.spol = spol;
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Gradjanin{" + "ime=" + ime + ", prezime=" + prezime + ", oib=" + oib + ", spol=" + spol + ", adresa=" + adresa + '}';
    }
}

class Adresa {
    String ulica;
    String grad;
    int postanskiBroj;

    public Adresa(String ulica, String grad, int postanskiBroj) {
        this.ulica = ulica;
        this.grad = grad;
        this.postanskiBroj = postanskiBroj;
    }

    @Override
    public String toString() {
        return "Adresa{" + "ulica=" + ulica + ", grad=" + grad + ", postanskiBroj=" + postanskiBroj + '}';
    }
}