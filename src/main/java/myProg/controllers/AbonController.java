package myProg.controllers;

import lombok.extern.slf4j.Slf4j;
import myProg.domain.Abon;
import myProg.services.AbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@Lazy
@Slf4j
public class AbonController {


    private final AbonService abonService;

    @Autowired
    public AbonController(AbonService abonService) {
        this.abonService = abonService;
    }


    @RequestMapping("/abon")
    @ResponseStatus(code = HttpStatus.OK)
    public String welcome() {//Welcome page, non-rest
        return "Welcome to AbonController Example.";
    }

    @GetMapping(
            path = "/abon/{id}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE, // jackson-dataformat-xml dependency in build.gradle needed!!!
                    MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Abon> abonEntity(@PathVariable Long id) {
        log.info("{}: initialization started", getClass().getSimpleName());
        log.info("id={}", id);

        return abonService.findById(id)
                .map(ResponseEntity::ok) //  ResponseEntity.status(HttpStatus.OK).body(entity);
                .orElseGet(() -> buildBadResponse("Abon not found by ID")); // Формирование ответа и ошибки вручную
    }

    @NonNull
    private ResponseEntity<Abon> buildBadResponse(String msg) {
        return ResponseEntity
                .badRequest()
                .header("Error message", msg)
                .build();
    }

    @GetMapping(
            path = "/abonById/{id}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Abon abonEntityById(@PathVariable Long id) {
        // http://www.baeldung.com/exception-handling-for-rest-with-spring
        // http://www.baeldung.com/global-error-handler-in-a-spring-rest-api

        return abonService.findById(id)
                .orElseThrow(() -> new AbonNotFoundException(id)); // Формирование ответа и ошибки Спрингом (ошибка через @ResponseStatus в Exception)
    }
}
