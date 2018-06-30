package br.com.welson.clinic.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class FileXMLService {

    private Document document;

    public FileXMLService(String filename) {
        InputStream xml = getClass().getClassLoader().getResourceAsStream(filename);
        try {
            configure(xml);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void configure(InputStream xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(xml);
    }

    public String findValue(String key) {
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("query");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                if (e.getAttribute("id").equals(key)) {
                    return e.getTextContent();
                }
            }
        }
        return null;
    }
}
