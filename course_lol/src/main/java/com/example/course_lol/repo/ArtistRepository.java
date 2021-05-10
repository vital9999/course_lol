package com.example.course_lol.repo;

import com.example.course_lol.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository  extends CrudRepository<Artist, Integer> {
}
