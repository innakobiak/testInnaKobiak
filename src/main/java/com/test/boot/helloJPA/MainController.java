package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestBody        Person p
           //RequestParam String lastName,
           //RequestParam String email,
           //RequestParam Integer edad,
           //RequestParam Collection<Phone> phones
    ) throws IOException{

        personRepository.save(p);
        response.setStatus(201);
        response.setHeader("location","/person/"+ p.getId());
        return p;
    }
    @PutMapping(path = "/{id}")
    public  Object updatePerson(
            HttpServletResponse response,
            @PathVariable Integer id,
            @RequestBody  Person up


    ) throws IOException {
        Person np = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
        np.setEdad(up.getEdad());
        np.setEmail(up.getEmail());
        np.setFirstName(up.getFirstName());
        np.setLastName(up.getLastName());
        np.setPhones(up.getPhones());
        personRepository.save(np);
        response.setStatus(200);
        return np;

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
        return new CustomResponse(null,true,operson);
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
            @PathVariable Integer id, @RequestBody Phone newphone
    ) throws IOException{
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundExcception(id));
            phoneRepository.save(newphone);
            response.setStatus(201);
            return new CustomResponse(null,true,person.getPhones());
    }

    //actualizaPhone
    @PutMapping(path="/{id}/phones/{phoneId}")
    public  Object updatePhone(
            HttpServletResponse response,
            @PathVariable Integer id,
            @RequestBody Phone newphone
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
            @RequestBody Phone uphone

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

