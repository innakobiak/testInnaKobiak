package com.test.boot.helloJPA;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DirRepository extends CrudRepository <Direccion, Integer>{
    List<Direccion> findByPerson(Person person);
    Optional<Direccion> findByPersonAndId(Person person, Integer id);
}
