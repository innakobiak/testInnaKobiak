package com.test.boot.helloJPA;

public class PhoneNotFoundExcception extends RuntimeException {
    public PhoneNotFoundExcception(Integer id){
        super("Phone " + id + " no encontrado");
    }
}
