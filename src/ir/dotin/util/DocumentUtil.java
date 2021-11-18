package ir.dotin.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author : Bahar Zolfaghari
 **/
public interface DocumentUtil {

    /**
     * this method initialize and create a document for parse xml file.
     * @param pathFile path xml file that we want parse it.
     * @return Document this returns created document.
     * */
     static Document getDocument(String pathFile) {
        Document document = null;
        try {
            File depositsInfoFile = new File(pathFile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(depositsInfoFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return document;
    }
}
