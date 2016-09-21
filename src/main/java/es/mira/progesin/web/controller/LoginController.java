package es.mira.progesin.web.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.web.beans.UserBean;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@Setter
@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
    
    
    public String login() {
        return "login";
    }
    
   
    @RequestMapping(method = RequestMethod.GET, value="/logout")
    public String logout() {
        return "logout";
    }
  
}
