/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import khai.edu.automatization.lesson.web.ajax.AjaxStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/test")
public class TestController {
    
    @RequestMapping("")
    public String get(){
        return "test/test";
    }
    
    @RequestMapping(value = "/getJson", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus getJson(){
        AjaxStatus st = new AjaxStatus();
        st.setMessage("Hello this is ajax answer");
        return st;
    }
}
