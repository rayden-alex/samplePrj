package myProg.controllers;

import myProg.jpa.entity.AbonEntity;
import myProg.services.AbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbonController {


    private final AbonService abonService;

    @Autowired
    public AbonController(AbonService abonService) {
        this.abonService = abonService;
    }


    @RequestMapping("/")
    public String welcome() {//Welcome page, non-rest
        return "Welcome to RestTemplate Example.";
    }

    @RequestMapping(path = "/abon/{id}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE})
    public AbonEntity abonEntity(@PathVariable Long id) {//REST Endpoint.
        return abonService.findById(id);
    }
}
