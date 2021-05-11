package com.example.course_lol.repo;

import com.example.course_lol.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
