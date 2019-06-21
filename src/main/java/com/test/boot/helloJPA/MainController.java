package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/person")
public class MainController {

    @Autowired
    private PersonRepository personRepository;

    //@RequestMapping(method = RequestMethod.GET.path="/all")
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping(path = "/search")
    public @ResponseBody Person getPerson(@RequestParam Integer id) {
        Person p = (Person)personRepository.findById(id);
        return p;
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addPerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer edad
    ) {

        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setEdad(edad);

        personRepository.save(p);
        return "OK";
    }
}

