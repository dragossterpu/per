package es.mira.progesin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.services.IUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
    @Autowired
    IUserService userRepository;

    @RequestMapping(method = RequestMethod.GET, value="/listUsers")
    public String listAll(Model model) {
        try {
            model.addAttribute("users", userRepository.findAll());
            return "users";
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/usuarios")
    public String showUserPage() {
    	return "administracion/usuarios/usuarios";
    }
}
