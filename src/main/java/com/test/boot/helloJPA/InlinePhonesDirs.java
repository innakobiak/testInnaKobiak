package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name="inlinePhonesDirs", types={Person.class})
public interface InlinePhonesDirs {
    String getFirstName();
    String getLastName();
    String getCompany();
    Collection<Phone> getPhones();
    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();
    Collection<Direccion> getDirs();

}
