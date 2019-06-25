package com.test.boot.helloJPA;

public class DirNotFoundExcception extends RuntimeException {
    public DirNotFoundExcception(Integer id){
        super("Direccion " + id + " no encontrada");
    }
}
