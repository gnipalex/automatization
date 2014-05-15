/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.security;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author Alex
 */
public class LoginForm {
    @NotEmpty(message = "Поле логин не должно быть пустым")
    private String login;
    @NotEmpty(message = "Поле пароль не должно быть пустым")
    private String password;
    
    private Boolean remember;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }
}
