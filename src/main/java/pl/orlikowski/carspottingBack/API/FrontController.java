package pl.orlikowski.carspottingBack.API;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
//Controller providing html front-end to the selected locations - uses Thymeleaf
public class FrontController {

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping(path="/addspot")
    public String getAddspot() {
        return "addspot";
    }

    @GetMapping(path="/login")
    public String getLogin() { return "login"; }

    @GetMapping(path="/register")
    public String getRegister() { return "register"; }

    @GetMapping(path="/search")
    public String getSearch() { return "search"; }

    @GetMapping(path="/myspots")
    public String getMySPots() { return "myspots"; }

    @GetMapping(path="/passreset")
    public String getTestTemplate() { return "passreset"; }







}
