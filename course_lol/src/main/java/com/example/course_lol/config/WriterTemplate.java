package com.example.course_lol.config;

import com.example.course_lol.model.Album;
import com.example.course_lol.repo.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

public abstract class WriterTemplate {

    public void getInfo(List<Album> albums) throws ParserConfigurationException, TransformerException {
        for(int i =0; i< albums.size();i++) {
            if (albums.get(i).getMin() < 10){
                albums.remove(i);i--;
            }
        }
        print(albums);
    }
    public abstract void print(List<Album> albums) throws ParserConfigurationException, TransformerException;
}
