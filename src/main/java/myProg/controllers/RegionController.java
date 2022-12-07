package myProg.controllers;

import lombok.extern.slf4j.Slf4j;
import myProg.domain.Region;
import myProg.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Lazy
@Slf4j
public class RegionController {
    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }


    @ModelAttribute("allRegions")
    public List<Region> populateRegions() {
        return regionService.findAll();
    }


    @GetMapping({"/", "/regionmng"})
    public String showRegions(Model model) {
        //model.addAttribute("allRegions", regionService.findAll());
        return "regionmng";
    }
    @RequestMapping({"/logout"})
    public String logout() {
        return "login";
    }

    @RequestMapping({"/login"})
    public String login() {
        return "login";
    }
}
