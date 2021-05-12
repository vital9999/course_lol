package com.example.course_lol.repo;

import com.example.course_lol.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findByAlbum_id(int album_id);
    List<Song> findAllByAlbum_id(int album_id);

}
