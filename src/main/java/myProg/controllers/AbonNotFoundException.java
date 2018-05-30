package myProg.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AbonNotFoundException extends RuntimeException {

    public AbonNotFoundException(Long id) {
        super("could not find Abon with id=" + id);
    }
}