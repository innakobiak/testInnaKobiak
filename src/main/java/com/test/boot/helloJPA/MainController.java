package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public CustomResponse getPersons(HttpServletResponse response) throws IOException{
        CustomResponse cr = new CustomResponse();
        Iterable<Person> lista = personRepository.findAll();
        cr.setData(lista);
        cr.setSuccess(true);
        cr.setMessage(null);
        return new CustomResponse(null,true,lista);
    }


    @GetMapping(path = "/{id}")
    public  CustomResponse getPerson(
            HttpServletResponse response,
            @PathVariable Integer id) throws IOException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
            response.setStatus(201);
            return new CustomResponse(null,true,person);
    }

    //AÑADIR UNO: POST
    //@PostMapping(path = "/add")
    @PostMapping
    public  CustomResponse addPerson(
            HttpServletResponse response,
            @Validated @RequestBody Person p
    ) throws IOException{
        personRepository.save(p);
        response.setStatus(201);
        //response.setHeader("location","/person/"+ p.getId());
        return new CustomResponse(null,true,p);
    }
    @PutMapping(path = "/{id}")
    public  Object updatePerson(
            HttpServletResponse response,
            @PathVariable Integer id,
            @Validated @RequestBody  Person p
    ) throws IOException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
        person.setEdad(p.getEdad());
        person.setEmail(p.getEmail());
        person.setFirstName(p.getFirstName());
        person.setLastName(p.getLastName());
        person.setCompany(p.getCompany());
        //person.setPhones(p.getPhones());
        personRepository.save(person);
        response.setStatus(200);
        return person;

    }
    //BORRA: DELETE
    @DeleteMapping(path = "/{id}")
    public CustomResponse deletePerson(
            HttpServletResponse response,
            @PathVariable  Integer id
    ) throws IOException {
        Person operson = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
        personRepository.delete(operson);
        response.setStatus(204);
        return new CustomResponse(null,true,null);
    }

    //PHONES
    @GetMapping(path="/{id}/phones")
    public CustomResponse recuperaPhones(
            HttpServletResponse response,
            @PathVariable Integer id) throws IOException {
        List<Phone> lista = phoneRepository.findByPerson(new Person(id));
        response.setStatus(201);
        return new CustomResponse(null,true, lista);
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
    }

    //add phone
    @PostMapping(path="/{id}/phones")
    public  CustomResponse addPhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @Validated @RequestBody Phone newphone
    ) throws IOException{
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
            phoneRepository.save(newphone);
            response.setStatus(201);
            return new CustomResponse(null,true, person.getPhones());
    }

    //actualizaPhone
    @PutMapping(path="/{id}/phones/{phoneId}")
    public  Object updatePhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @Validated @RequestBody Phone newphone
    ) throws IOException {
        Phone phone = getPhoneIfExist(id, newphone.getId(), newphone.getTipo(), newphone.getNumber());
        phoneRepository.save(phone);
        response.setStatus(200);
        return new CustomResponse(null, true, phone);

    }

    //borraOnePhone
    @DeleteMapping(path="/{id}/phones/{phoneId}")
    public  Object deletePhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @Validated @RequestBody Phone uphone

    ) throws IOException {

        Phone phone = getPhoneIfExist(id, uphone.getId(), uphone.getTipo(), uphone.getNumber());
        phoneRepository.delete(phone);
        response.setStatus(204);
        return new CustomResponse(null, true, phone);

    }

    private Phone getPhoneIfExist(@PathVariable Integer id, @PathVariable Integer phoneId, @RequestParam String tipo, @RequestParam String number) throws IOException{
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

