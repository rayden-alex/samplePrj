package myProg.controllers;

import myProg.jpa.AbonEntity;
import myProg.services.AbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                    "application/xml; charset=UTF-8",
                    MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<AbonEntity> abonEntityList(@PathVariable Long id) {//REST Endpoint.

        List<AbonEntity> abonEntityList = abonService.findById(id);
        return abonEntityList;
    }
}
