package com.example.course_lol.config;

import com.example.course_lol.model.Album;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TxtPrint extends WriterTemplate{


    @Override
    public void print(List<Album> albums) {
        try(FileWriter writer = new FileWriter("albums", false))
        {
            for(int i = 0;i<albums.size();i++) {
                writer.write(albums.get(i).getName());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
