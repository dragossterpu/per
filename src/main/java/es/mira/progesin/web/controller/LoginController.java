package es.mira.progesin.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class LoginController {

	private static final String LOGIN = "login";

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login() {
		return LOGIN;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return LOGIN;
	}

}
