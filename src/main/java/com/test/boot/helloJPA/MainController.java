package com.test.boot.helloJPA;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/persons")
public class MainController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    //@GetMapping(path = "/all")
    //@GetMapping(path = "/")
    /*
    @GetMapping
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }*/
    @GetMapping
    public CustomResponse getPersons() throws IOException{
        CustomResponse cr = new CustomResponse();
        Iterable<Person> ip = personRepository.findAll();
        if (ip != null){
            cr.setData(ip);
            cr.setSuccess(true);
            cr.setMessage(null);
        }else {
            cr.setData(null);
            cr.setSuccess(false);
            cr.setMessage("No hay personas");
        }
        return cr;
    }


    @GetMapping(path = "/{id}")
    public  CustomResponse getPerson(
            HttpServletResponse response,
            @PathVariable(name="id") Integer id) throws IOException {
        Optional<Person> p = personRepository.findById(id);
        if (p.isPresent()) {
            Person person = p.get();
            response.setStatus(201);
            return new CustomResponse(null,true,p);
        }
        response.sendError(404, "Id no encontrado");
        return new CustomResponse("No encontrado",false,null);
    }

    //AÑADIR UNO: POST
    //@PostMapping(path = "/add")
    @PostMapping
    public  Person addPerson(
            HttpServletResponse response,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer edad,
            @RequestParam Collection<Phone> phones
    ) throws IOException{

        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setEdad(edad);
        p.setPhones(phones);
        personRepository.save(p);
        response.setStatus(201);
        response.setHeader("location","/person/"+ p.getId());
        return p;
    }
    @PutMapping(path = "/{id}")
    public  Object updatePerson(
            HttpServletResponse response,
            @PathVariable(name="id")  Integer id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer edad,
            @RequestParam Collection<Phone> phones

    ) throws IOException {
        Optional<Person> p = personRepository.findById(id);
        if(p.isPresent()) {
            Person np = p.get();
            np.setEdad(edad);
            np.setEmail(email);
            np.setFirstName(firstName);
            np.setLastName(lastName);
            np.setPhones(phones);
            personRepository.save(np);
            response.setStatus(201);
            return p;
        }
        response.sendError(404, "Id no encontrado");
        return null;
    }
    //BORRA: DELETE
    @DeleteMapping(path = "/{id}")
    public CustomResponse deletePerson(
            HttpServletResponse response,
            @PathVariable  Integer id
    ) throws IOException {
        Person operson = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
        return new CustomResponse(null,true,operson);
    }

    //PHONES
    @GetMapping(path="/{id}/phones")
    public CustomResponse recuperaPhones(
            HttpServletResponse response,
            @PathVariable Integer id) throws IOException {
        Person pp = new Person(id);
        List<Phone> lista = phoneRepository.findByPerson(pp);
        response.setStatus(201);
        return new CustomResponse(null,true,lista);
    }
    @GetMapping(path="/{id}/phones/{phoneId}")
    public CustomResponse recuperaPhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @PathVariable Integer phoneId
    ) throws IOException {
        Person operson = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
        Phone phone = phoneRepository.findByPersonAndId(new Person(id),phoneId)
                .orElseThrow(() -> new PhoneNotFoundExcception(phoneId));
        response.setStatus(200);
        return new CustomResponse(null, true, phone);

        //response.sendError(404, "No encontrado");
        //return new CustomResponse("No encontrado",false,null);
    }

    //add phone
    @PostMapping(path="/{id}/phones")
    public  CustomResponse addPhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @RequestParam String tipo,
            @RequestParam String number
    ) throws IOException{
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
            Phone phone = new Phone();
            phone.setNumber(number);
            phone.setTipo(tipo);
            phone.setPerson(person);
            phoneRepository.save(phone);
            response.setStatus(201);
            return new CustomResponse(null,true,person.getPhones());
        //response.setStatus(404);
        //return new CustomResponse("Error",true,null);
    }

    //actualizaPhone
    @PutMapping(path="/{id}/phones/{phoneId}")
    public  Object updatePhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @PathVariable Integer phoneId,
            @RequestParam String tipo,
            @RequestParam String number
    ) throws IOException {
        Phone phone = comprobarSiExiste(id, phoneId, tipo, number);
        phoneRepository.save(phone);
        response.setStatus(200);
        return new CustomResponse(null, true, phone);

    }

    //borraOnePhone
    @DeleteMapping(path="/{id}/phones/{phoneId}")
    public  Object deletePhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @PathVariable Integer phoneId,
            @RequestParam String tipo,
            @RequestParam String number
    ) throws IOException {

        Phone phone = comprobarSiExiste(id, phoneId, tipo, number);
        phoneRepository.delete(phone);
        response.setStatus(204);
        return new CustomResponse(null, true, phone);
        //response.sendError(404, "No encontrado");
        //return new CustomResponse("No encontrado",false,null);

    }

    private Phone comprobarSiExiste(@PathVariable Integer id, @PathVariable Integer phoneId, @RequestParam String tipo, @RequestParam String number) throws IOException{
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
        Phone phone = phoneRepository.findByPersonAndId(new Person(id), phoneId)
                .orElseThrow(() -> new PhoneNotFoundExcception(phoneId));
        phone.setNumber(number);
        phone.setTipo(tipo);
        phone.setPerson(person);
        return phone;
    }

}

