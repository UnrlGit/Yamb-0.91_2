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
import sample.models.YambLine;
import sample.models.YambPaper;


public class XmlRead {

    public static List<YambPaper> getYambPaperXml() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        InputStream xmlInput  = new FileInputStream("reviewYamb.xml");

        SAXParser saxParser = factory.newSAXParser();
        SaxHandler2 handler   = new SaxHandler2();
        saxParser.parse(xmlInput, handler);

        return handler.yambPapers();
    }
}

class SaxHandler2 extends DefaultHandler {
    private List<YambPaper> yambPapers = new LinkedList<>();

    private YambPaper trenutniPapir;
    private YambLine trenutniDown;
    private YambLine trenutniUp;
    private YambLine trenutniFree;
    private YambLine trenutniCall;
    private String trenutniElement;

    public List<YambPaper> yambPapers() { return yambPapers; }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        trenutniElement = qName;

        if("YambPapers".equals(qName))
        {return;}

        if ("YambPaper".equals(trenutniElement)) {
            trenutniPapir = new YambPaper();
            return;
        }

        if ("lineDown".equals(trenutniElement)) {
            trenutniDown = new YambLine();
            return;
        }

        if ("lineUp".equals(trenutniElement)) {
            trenutniUp = new YambLine();
            return;
        }

        if ("lineFree".equals(trenutniElement)) {
            trenutniFree = new YambLine();
            return;
        }

        if ("lineCall".equals(trenutniElement)) {
            trenutniCall = new YambLine();
            return;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("YambPapers".equals(qName))
        {return;}
        if ("YambPaper".equals(qName)) {
            yambPapers.add(trenutniPapir);
            trenutniPapir = null;
            return;
        }

        if ("lineDown".equals(qName)) {
            trenutniPapir.setDown(trenutniDown);
            trenutniDown = null;
            return;
        }

        if ("lineUp".equals(qName)) {
            trenutniPapir.setUp(trenutniUp);
            trenutniUp = null;
            return;
        }

        if ("lineFree".equals(qName)) {
            trenutniPapir.setFree(trenutniFree);
            trenutniFree = null;
            return;
        }

        if ("lineCall".equals(qName)) {
            trenutniPapir.setCall(trenutniCall);
            trenutniCall = null;
            return;
        }

        trenutniElement = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space

        switch(trenutniElement) {
            //DOWN
            case "onesD" : trenutniDown.setOnes(Integer.valueOf(value));
                break;
            case "twosD" : trenutniDown.setTwos(Integer.valueOf(value)); break;
            case "threesD" : trenutniDown.setThrees(Integer.valueOf(value)); break;
            case "foursD" : trenutniDown.setFours(Integer.valueOf(value)); break;
            case "fivesD" : trenutniDown.setFives(Integer.valueOf(value)); break;
            case "sixesD" : trenutniDown.setSixes(Integer.valueOf(value)); break;
            case "maxD" : trenutniDown.setMax(Integer.valueOf(value)); break;
            case "minD" : trenutniDown.setMin(Integer.valueOf(value)); break;
            case "twoPairsD" : trenutniDown.setTwoPairs(Integer.valueOf(value)); break;
            case "straightD" : trenutniDown.setStraight(Integer.valueOf(value)); break;
            case "fullD" : trenutniDown.setFull(Integer.valueOf(value)); break;
            case "pokerD" : trenutniDown.setPoker(Integer.valueOf(value)); break;
            case "yambD" : trenutniDown.setYamb(Integer.valueOf(value)); break;
            //UP
            case "onesU" : trenutniUp.setOnes(Integer.valueOf(value)); break;
            case "twosU" : trenutniUp.setTwos(Integer.valueOf(value)); break;
            case "threesU" : trenutniUp.setThrees(Integer.valueOf(value)); break;
            case "foursU" : trenutniUp.setFours(Integer.valueOf(value)); break;
            case "fivesU" : trenutniUp.setFives(Integer.valueOf(value)); break;
            case "sixesU" : trenutniUp.setSixes(Integer.valueOf(value)); break;
            case "maxU" : trenutniUp.setMax(Integer.valueOf(value)); break;
            case "minU" : trenutniUp.setMin(Integer.valueOf(value)); break;
            case "twoPairsU" : trenutniUp.setTwoPairs(Integer.valueOf(value)); break;
            case "straightU" : trenutniUp.setStraight(Integer.valueOf(value)); break;
            case "fullU" : trenutniUp.setFull(Integer.valueOf(value)); break;
            case "pokerU" : trenutniUp.setPoker(Integer.valueOf(value)); break;
            case "yambU" : trenutniUp.setYamb(Integer.valueOf(value)); break;
            //FREE
            case "onesF" : trenutniFree.setOnes(Integer.valueOf(value)); break;
            case "twosF" : trenutniFree.setTwos(Integer.valueOf(value)); break;
            case "threesF" : trenutniFree.setThrees(Integer.valueOf(value)); break;
            case "foursF" : trenutniFree.setFours(Integer.valueOf(value)); break;
            case "fivesF" : trenutniFree.setFives(Integer.valueOf(value)); break;
            case "sixesF" : trenutniFree.setSixes(Integer.valueOf(value)); break;
            case "maxF" : trenutniFree.setMax(Integer.valueOf(value)); break;
            case "minF" : trenutniFree.setMin(Integer.valueOf(value)); break;
            case "twoPairsF" : trenutniFree.setTwoPairs(Integer.valueOf(value)); break;
            case "straightF" : trenutniFree.setStraight(Integer.valueOf(value)); break;
            case "fullF" : trenutniFree.setFull(Integer.valueOf(value)); break;
            case "pokerF" : trenutniFree.setPoker(Integer.valueOf(value)); break;
            case "yambF" : trenutniFree.setYamb(Integer.valueOf(value)); break;
            //CALL
            case "onesC" : trenutniCall.setOnes(Integer.valueOf(value)); break;
            case "twosC" : trenutniCall.setTwos(Integer.valueOf(value)); break;
            case "threesC" : trenutniCall.setThrees(Integer.valueOf(value)); break;
            case "foursC" : trenutniCall.setFours(Integer.valueOf(value)); break;
            case "fivesC" : trenutniCall.setFives(Integer.valueOf(value)); break;
            case "sixesC" : trenutniCall.setSixes(Integer.valueOf(value)); break;
            case "maxC" : trenutniCall.setMax(Integer.valueOf(value)); break;
            case "minC" : trenutniCall.setMin(Integer.valueOf(value)); break;
            case "twoPairsC" : trenutniCall.setTwoPairs(Integer.valueOf(value)); break;
            case "straightC" : trenutniCall.setStraight(Integer.valueOf(value)); break;
            case "fullC" : trenutniCall.setFull(Integer.valueOf(value)); break;
            case "pokerC" : trenutniCall.setPoker(Integer.valueOf(value)); break;
            case "yambC" : trenutniCall.setYamb(Integer.valueOf(value)); break;
        }
    }
}
