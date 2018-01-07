package sample.XML;

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
import sample.models.YambPaper;


public class XmlCreate {
    public static void writeYambPapersToXml(List<YambPaper> yambPapers) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {

        Document xmlDokument = kreirajDokument(yambPapers);

        spremi(xmlDokument, "reviewYamb.xml");
    }

    private static Document kreirajDokument(List<YambPaper> yambPapers) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        Element yambPaper = doc.createElement("YambPapers");

        doc.appendChild(yambPaper);

        List<YambPaper> yambPaperList = yambPapers;

        for(YambPaper paper : yambPaperList) {
            Element gradjaninElement = napraviNoviGradjaniElement(doc, paper);
            yambPaper.appendChild(gradjaninElement);
        }

        return doc;
    }
    private static Element napraviNoviGradjaniElement(Document doc, YambPaper g) {
        Element yambPaper = doc.createElement("YambPaper");
        // DOWN

        //DOWN
        Element lineDown = doc.createElement("lineDown");

        //SINGLES
        Element ones = doc.createElement("onesD");
        ones.appendChild(doc.createTextNode(String.valueOf(g.getDown().getOnes())));
        lineDown.appendChild(ones);
        Element twos = doc.createElement("twosD");
        twos.appendChild(doc.createTextNode(String.valueOf(g.getDown().getTwos())));
        lineDown.appendChild(twos);
        Element threes = doc.createElement("threesD");
        threes.appendChild(doc.createTextNode(String.valueOf(g.getDown().getThrees())));
        lineDown.appendChild(threes);
        Element fours = doc.createElement("foursD");
        fours.appendChild(doc.createTextNode(String.valueOf(g.getDown().getFours())));
        lineDown.appendChild(fours);
        Element fives = doc.createElement("fivesD");
        fives.appendChild(doc.createTextNode(String.valueOf(g.getDown().getFives())));
        lineDown.appendChild(fives);
        Element sixes = doc.createElement("sixesD");
        sixes.appendChild(doc.createTextNode(String.valueOf(g.getDown().getSixes())));
        lineDown.appendChild(sixes);
        //MINMAX
        Element max = doc.createElement("maxD");
        max.appendChild(doc.createTextNode(String.valueOf(g.getDown().getMax())));
        lineDown.appendChild(max);
        Element min = doc.createElement("minD");
        min.appendChild(doc.createTextNode(String.valueOf(g.getDown().getMin())));
        lineDown.appendChild(min);
        //SPECIALS
        Element twoPairs = doc.createElement("twoPairsD");
        twoPairs.appendChild(doc.createTextNode(String.valueOf(g.getDown().getTwoPairs())));
        lineDown.appendChild(twoPairs);
        Element straight = doc.createElement("straightD");
        straight.appendChild(doc.createTextNode(String.valueOf(g.getDown().getStraight())));
        lineDown.appendChild(straight);
        Element full = doc.createElement("fullD");
        full.appendChild(doc.createTextNode(String.valueOf(g.getDown().getFull())));
        lineDown.appendChild(full);
        Element poker = doc.createElement("pokerD");
        poker.appendChild(doc.createTextNode(String.valueOf(g.getDown().getPoker())));
        lineDown.appendChild(poker);
        Element yamb = doc.createElement("yambD");
        yamb.appendChild(doc.createTextNode(String.valueOf(g.getDown().getYamb())));
        lineDown.appendChild(yamb);
        //FINAL
        yambPaper.appendChild(lineDown);
        //UP
        Element lineUp = doc.createElement("lineUp");

        //SINGLES
        Element onesD = doc.createElement("onesU");
        onesD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getOnes())));
        lineUp.appendChild(onesD);
        Element twosD = doc.createElement("twosU");
        twosD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getTwos())));
        lineUp.appendChild(twosD);
        Element threesD = doc.createElement("threesU");
        threesD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getThrees())));
        lineUp.appendChild(threesD);
        Element foursD = doc.createElement("foursU");
        foursD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getFours())));
        lineUp.appendChild(foursD);
        Element fivesD = doc.createElement("fivesU");
        fivesD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getFives())));
        lineUp.appendChild(fivesD);
        Element sixesD = doc.createElement("sixesU");
        sixesD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getSixes())));
        lineUp.appendChild(sixesD);
        //MINMAX
        Element maxD = doc.createElement("maxU");
        maxD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getMax())));
        lineUp.appendChild(maxD);
        Element minD = doc.createElement("minU");
        minD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getMin())));
        lineUp.appendChild(minD);
        //SPECIALS
        Element twoPairsD = doc.createElement("twoPairsU");
        twoPairsD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getTwoPairs())));
        lineUp.appendChild(twoPairsD);
        Element straightD = doc.createElement("straightU");
        straightD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getStraight())));
        lineUp.appendChild(straightD);
        Element fullD = doc.createElement("fullU");
        fullD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getFull())));
        lineUp.appendChild(fullD);
        Element pokerD = doc.createElement("pokerU");
        pokerD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getPoker())));
        lineUp.appendChild(pokerD);
        Element yambD = doc.createElement("yambU");
        yambD.appendChild(doc.createTextNode(String.valueOf(g.getUp().getYamb())));
        lineUp.appendChild(yambD);
        //FINAL
        yambPaper.appendChild(lineUp);
        //FREE
        Element lineFree = doc.createElement("lineFree");

        //SINGLES
        Element onesF = doc.createElement("onesF");
        onesF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getOnes())));
        lineFree.appendChild(onesF);
        Element twosF = doc.createElement("twosF");
        twosF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getTwos())));
        lineFree.appendChild(twosF);
        Element threesF = doc.createElement("threesF");
        threesF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getThrees())));
        lineFree.appendChild(threesF);
        Element foursF = doc.createElement("foursF");
        foursF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getFours())));
        lineFree.appendChild(foursF);
        Element fivesF = doc.createElement("fivesF");
        fivesF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getFives())));
        lineFree.appendChild(fivesF);
        Element sixesF = doc.createElement("sixesF");
        sixesF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getSixes())));
        lineFree.appendChild(sixesF);
        //MINMAX
        Element maxF = doc.createElement("maxF");
        maxF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getMax())));
        lineFree.appendChild(maxF);
        Element minF = doc.createElement("minF");
        minF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getMin())));
        lineFree.appendChild(minF);
        //SPECIALS
        Element twoPairsF = doc.createElement("twoPairsF");
        twoPairsF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getTwoPairs())));
        lineFree.appendChild(twoPairsF);
        Element straightF = doc.createElement("straightF");
        straightF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getStraight())));
        lineFree.appendChild(straightF);
        Element fullF = doc.createElement("fullF");
        fullF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getFull())));
        lineFree.appendChild(fullF);
        Element pokerF = doc.createElement("pokerF");
        pokerF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getPoker())));
        lineFree.appendChild(pokerF);
        Element yambF = doc.createElement("yambF");
        yambF.appendChild(doc.createTextNode(String.valueOf(g.getFree().getYamb())));
        lineFree.appendChild(yambF);
        //FINAL
        yambPaper.appendChild(lineFree);

        //CALL
        Element lineCall = doc.createElement("lineCall");

        //SINGLES
        Element onesC = doc.createElement("onesC");
        onesC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getOnes())));
        lineCall.appendChild(onesC);
        Element twosC = doc.createElement("twosC");
        twosC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getTwos())));
        lineCall.appendChild(twosC);
        Element threesC = doc.createElement("threesC");
        threesC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getThrees())));
        lineCall.appendChild(threesC);
        Element foursC = doc.createElement("foursC");
        foursC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getFours())));
        lineCall.appendChild(foursC);
        Element fivesC = doc.createElement("fivesC");
        fivesC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getFives())));
        lineCall.appendChild(fivesC);
        Element sixesC = doc.createElement("sixesC");
        sixesC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getSixes())));
        lineCall.appendChild(sixesC);
        //MINMAX
        Element maxC = doc.createElement("maxC");
        maxC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getMax())));
        lineCall.appendChild(maxC);
        Element minC = doc.createElement("minC");
        minC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getMin())));
        lineCall.appendChild(minC);
        //SPECIALS
        Element twoPairsC = doc.createElement("twoPairsC");
        twoPairsC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getTwoPairs())));
        lineCall.appendChild(twoPairsC);
        Element straightC = doc.createElement("straightC");
        straightC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getStraight())));
        lineCall.appendChild(straightC);
        Element fullC = doc.createElement("fullC");
        fullC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getFull())));
        lineCall.appendChild(fullC);
        Element pokerC = doc.createElement("pokerC");
        pokerC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getPoker())));
        lineCall.appendChild(pokerC);
        Element yambC = doc.createElement("yambC");
        yambC.appendChild(doc.createTextNode(String.valueOf(g.getCall().getYamb())));
        lineCall.appendChild(yambC);
        //FINAL
        yambPaper.appendChild(lineCall);

        return yambPaper;
    }

    private static void spremi(Document doc, String nazivDatoteke) throws TransformerConfigurationException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("reviewYamb.xml"));

        transformer.transform(source, result);
        transformer.transform(source, new StreamResult(System.out));
    }


    private static List<YambPaper> getYambPapers() {
        List<YambPaper> yambPaperList = new LinkedList<>();
        YambPaper p = new YambPaper();
        p.getDown().setOnes(6);
        yambPaperList.add(new YambPaper());
        yambPaperList.add(p);

        return yambPaperList;
    }
}

