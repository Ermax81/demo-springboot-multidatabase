package com.example.mariadb.demo.hobby;

import com.example.mariadb.demo.hobby.Hobby;
import com.example.mariadb.demo.hobby.HobbyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HobbyService {

    private HobbyRepository hobbyRepository;

    public void add(String hobbyName) {
        if (hobbyRepository.findByName(hobbyName).isPresent()) {
            // todo throw exception
        } else {
            Hobby newHobby = new Hobby();
            newHobby.setName(hobbyName);
            hobbyRepository.save(newHobby);
        }
    }

    public List<String> getAll() {
        List<Hobby> hobbies = hobbyRepository.findAll();
        List<String> hobbiesToDisplay = new ArrayList<>();
        for (Hobby item : hobbies) {
            hobbiesToDisplay.add(item.getName());
        }

        return hobbiesToDisplay;
    }

    public Optional<Hobby> get(String hobbyName) {
        return hobbyRepository.findByName(hobbyName);
    }

}
