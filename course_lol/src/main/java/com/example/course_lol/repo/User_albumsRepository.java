package com.example.course_lol.repo;

import com.example.course_lol.model.Album;
import com.example.course_lol.model.Song;
import com.example.course_lol.model.User_albums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface User_albumsRepository extends JpaRepository<User_albums, Integer> {
    List<User_albums> findAllByUserId(int userId);

}
