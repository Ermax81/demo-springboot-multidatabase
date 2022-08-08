package com.example.mariadb.demo.person;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;

    public List<String> getAll() {
        List<String> personsToDisplay = new ArrayList<>();
        List<Person> persons = personRepository.findAll();
        for (Person p: persons) {
            personsToDisplay.add(p.toString());
        }
        return personsToDisplay;
    }

    public void add(Person person) {
        if (person!=null) {
            Optional<Person> personInDb = personRepository.findPersonByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(person.getFirstName(), person.getLastName());
            if (personInDb.isEmpty()) {
                personRepository.save(person);
            }
        }
    }

}
