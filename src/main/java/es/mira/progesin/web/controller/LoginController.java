package es.mira.progesin.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String login2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
            // JSF ajax request. Return special XML response which instructs JavaScript that it should in turn perform a
            // redirect.
            response.setContentType("text/xml");
            response.getWriter().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                    .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", LOGIN);
            return null;
        } else {
            // Normal request. Perform redirect as usual.
            return LOGIN;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return LOGIN;
    }
    
}
