package es.mira.progesin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/recuperarPassword")
    public String recuperarPassword() {
        return "recuperarPassword";
    }

    @RequestMapping(method = RequestMethod.GET, value="/logout")
    public String logout() {
        return "logout";
    }

}
