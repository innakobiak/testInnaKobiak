package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path="/persons")
public class MainController {

    @Autowired
    private PersonRepository personRepository;

    //@RequestMapping(method = RequestMethod.GET.path="/all")
    //@GetMapping(path = "/all")
    //@GetMapping(path = "/")
    @GetMapping
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }
    @GetMapping(path = "/json")
    public CustomResponse getAllPersonsJSON() {
        return personRepository.findAll();
    }

    //BUSCA: GET
    @GetMapping(path = "/{id}")
    public  Person getPerson(
            HttpServletResponse response,
            @PathVariable(name="id") Integer id) throws IOException {
        Optional<Person> p = personRepository.findById(id);
        if(p.isPresent()) {
            return p.get();
        }
        response.sendError(404, "Id no encontrado");
        return null;
    }
    //AÑADIR UNO: POST
    //@PostMapping(path = "/add")
    @PostMapping
    public  Person addPerson(
            HttpServletResponse response,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer edad
    ) throws IOException{

        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setEdad(edad);

        personRepository.save(p);
        response.setStatus(201);
        response.setHeader("location","/person/"+ p.getId());
        return p;
    }
    //ACTUALIZA: PUT
    @PutMapping(path = "/{id}")
    public  Object updatePerson(
            HttpServletResponse response,
            @PathVariable(name="id")  Integer id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer edad

    ) throws IOException {
       Optional<Person> p = personRepository.findById(id);
       if(p.isPresent()) {
           Person np = p.get();
           np.setEdad(edad);
           np.setEmail(email);
           np.setFirstName(firstName);np.setLastName(lastName);
           personRepository.save(np);
           response.setStatus(201);
           return p;
        }
        response.sendError(404, "Id no encontrado");
        return null;
    }
    //BORRA: DELETE
    @DeleteMapping(path = "/{id}")
    public Object delPerson(
            HttpServletResponse response,
            @PathVariable(name="id")  Integer id
    ) throws IOException {
        Optional<Person> p = personRepository.findById(id);
        if(p.isPresent()) {
            personRepository.delete(p.get());
            response.setStatus(204);
            return null;
        }
        response.sendError(404, "Id no encontrado");
        return null;
    }
}

