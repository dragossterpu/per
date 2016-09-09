package es.mira.progesin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/login")
public class PrincipalController {
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "acceso/login";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/recuperarPassword")
    public String recuperarPassword() {
        return "acceso/recuperarPassword";
    }

    @RequestMapping(method = RequestMethod.GET, value="/logout")
    public String logout() {
        return "acceso/logout";
    }

}
