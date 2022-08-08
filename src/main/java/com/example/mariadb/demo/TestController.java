package com.example.mariadb.demo;

import com.example.mariadb.demo.hobby.Hobby;
import com.example.mariadb.demo.hobby.HobbyService;
import com.example.mariadb.demo.person.Person;
import com.example.mariadb.demo.person.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private HobbyService hobbyService;

    private PersonService personService;

    @GetMapping(value = "/init")
    public void init() {
        hobbyService.add("Football");
        hobbyService.add("Basketball");

        Hobby foot = null;
        Hobby basket = null;

        Person john = new Person("John","Doe",40);
        Person jane = new Person("Jane","Doe",40);

        if (hobbyService.get("Football").isPresent()) {
            foot = hobbyService.get("Football").get();
            john.setHobby(foot);
        }
        if (hobbyService.get("Basketball").isPresent()) {
            basket = hobbyService.get("Basketball").get();
            jane.setHobby(basket);
        }

        personService.add(john);
        personService.add(jane);
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public List<String> list() {
        List<String> hobbies = hobbyService.getAll();
        return hobbies;
    }

    @GetMapping(value = "/persons")
    @ResponseBody
    public List<String> getPersons() {
        return personService.getAll();
    }

}
