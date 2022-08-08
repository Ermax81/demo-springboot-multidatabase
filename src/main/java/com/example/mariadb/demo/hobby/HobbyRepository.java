package com.example.mariadb.demo.hobby;

import com.example.mariadb.demo.hobby.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HobbyRepository extends JpaRepository <Hobby, Integer> {

    List<Hobby> findAll();

    Optional<Hobby> findByName(String hobbyName);

}
