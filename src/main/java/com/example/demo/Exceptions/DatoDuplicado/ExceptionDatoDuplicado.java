package com.example.demo.Exceptions.DatoDuplicado;

import lombok.Getter;

public class ExceptionDatoDuplicado extends RuntimeException {

    @Getter
    private String campoDuplicado;
    public ExceptionDatoDuplicado(String message) {
        super(message);
    }

    public ExceptionDatoDuplicado(String message, String campoDuplicado){
        super(message);
        this.campoDuplicado = campoDuplicado;
    }
}
