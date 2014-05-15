/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.security;

import org.springframework.security.crypto.password.PasswordEncoder;



/**
 *
 * @author Alex
 */
public class AppUserPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence cs) {
        return cs.toString();
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return cs.toString().equals(string);
    }

    
    
}
