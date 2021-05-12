package com.example.course_lol.config;

import com.example.course_lol.model.Album;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class Parser {
    private static Parser parser;
    public static Parser getParser(){
        if(parser == null){
            parser = new Parser();
        }
        return parser;
    }
    public void parse(List<Album> albums) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();

        Element root = doc.createElement("root");
        root.setAttribute("xmlns", "http://www.javacore.ru/schemas/");
        doc.appendChild(root);

        Element item1 = doc.createElement("item");
        item1.setAttribute("val", albums.get(0).getName());
        root.appendChild(item1);

        Element item2 = doc.createElement("item");
        item2.setAttribute("val", "2");
        root.appendChild(item2);



        File file = new File("test.xml");

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }
}
