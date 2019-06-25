package com.test.boot.helloJPA;

public class PersonNotFoundExcception extends RuntimeException {
    public PersonNotFoundExcception (Integer id){
        super("Person " + id + " no encontrado");
    }
}
