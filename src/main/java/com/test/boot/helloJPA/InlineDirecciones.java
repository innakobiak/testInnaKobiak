package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name="inlineDirs", types={Direccion.class})
public interface InlineDirecciones {
    String getDireccion();
    String getNumero();
    String getCp();

    @Value("#{target.direccion} #{target.numero} #{target.cp}")
    String getFullDir();
}
