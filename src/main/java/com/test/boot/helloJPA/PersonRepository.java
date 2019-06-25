package com.test.boot.helloJPA;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(path = "personas", excerptProjection = InlinePhones.class)
@RepositoryRestResource( excerptProjection = InlinePhones.class)
public interface PersonRepository extends CrudRepository<Person,Integer> {
}
