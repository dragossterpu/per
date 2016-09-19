package es.mira.progesin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(method = RequestMethod.GET, value="/usuario")
public class UsuarioController {
    @RequestMapping(method = RequestMethod.GET)
    public String usuario() {
        return "usuario";
    }
    
   

}
