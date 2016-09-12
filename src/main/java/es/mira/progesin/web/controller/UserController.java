package es.mira.progesin.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.web.views.UserBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserBean userBean; 
    
    @RequestMapping(method = RequestMethod.GET, value="/listUsers")
    public String listAll(Model model) {
        try {
            model.addAttribute("users", userService.findAll());
            return "users";
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/usuarios")
    public String showUserPage() {
    	return "administracion/usuarios/usuarios";
    }
    
//    @RequestMapping(method = RequestMethod.GET, value="/miperfil")
//    public String verMiPerfil(Principal principal) {
//    	User user = userService.findOne(principal.getName());
//    	userBean.setUserBean(user);
//    	return "principal/miPerfil";
//    }
    
    @RequestMapping(method = RequestMethod.GET, value="/miperfil")
    public String verMiPerfil(Principal principal, Model model) {
    	User user = userService.findOne(principal.getName());
    	model.addAttribute("user", user);
    	return "principal/miPerfil";
    }
}
