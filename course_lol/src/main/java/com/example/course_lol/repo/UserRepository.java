package com.example.course_lol.repo;

import com.example.course_lol.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
