package com.giacomini.andrea.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception {

    private static final long serialVersionUID = -7181400112198472499L;

    private String messaggio = "Elemento Ricercato Non Trovato!";

    public NotFoundException()
    {
        super();
    }

    public NotFoundException(String messaggio)
    {
        super(messaggio);
        this.messaggio = messaggio;
    }
}