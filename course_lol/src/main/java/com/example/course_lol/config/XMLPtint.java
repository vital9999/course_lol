package com.example.course_lol.config;

import com.example.course_lol.model.Album;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLPtint extends WriterTemplate{
    @Override
    public void print(List<Album> albums) throws ParserConfigurationException, TransformerException {
        Parser.getParser().parse(albums);
    }
}
