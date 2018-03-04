package myProg.controllers;

import myProg.jpa.entity.AbonEntity;
import myProg.services.AbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AbonController {


    private final AbonService abonService;

    @Autowired
    public AbonController(AbonService abonService) {
        this.abonService = abonService;
    }


    @RequestMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public String welcome() {//Welcome page, non-rest
        return "Welcome to RestTemplate Example.";
    }

    @GetMapping(path = "/abon/{id}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE, // jackson-dataformat-xml dependency in build.gradle needed!!!
                    MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<AbonEntity> abonEntity(@PathVariable Long id) {
        AbonEntity entity = abonService.findById(id);

        if (entity == null) { // Формирование ответа и ошибки вручную
            return ResponseEntity
                    .badRequest()
                    .header("Error message", "Abon not found by ID")
                    .build();
        } else {
            return ResponseEntity.ok(entity);//  ResponseEntity.status(HttpStatus.OK).body(entity);
        }
    }

    @GetMapping(path = "/abonById/{id}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE})
    public AbonEntity abonEntityById(@PathVariable Long id) {
        // http://www.baeldung.com/exception-handling-for-rest-with-spring
        // http://www.baeldung.com/global-error-handler-in-a-spring-rest-api

        AbonEntity entity = abonService.findById(id);

        if (entity == null) { // Формирование ответа и ошибки Спрингом (ошибка через @ResponseStatus в Exception)
            throw new AbonNotFoundException(id);
        } else {
            return entity;
        }
    }
}
