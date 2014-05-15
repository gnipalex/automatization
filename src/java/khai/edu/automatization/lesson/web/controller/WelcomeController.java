/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Alex
 */
@Controller
public class WelcomeController {
    @RequestMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("aut", SecurityContextHolder.getContext().getAuthentication());
        return "welcome";
    }
    
    @RequestMapping("/about")
    public String about(Model model){
        return "about";
    }
}
