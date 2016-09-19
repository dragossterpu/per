package es.mira.progesin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController {
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
   
}
