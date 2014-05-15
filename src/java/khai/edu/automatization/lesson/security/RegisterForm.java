/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.security;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author Alex
 */
public class RegisterForm {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(max = 50, message = "Имя не долно превышать 50 символов")
    private String name;
    @NotEmpty(message = "Фамилия не долна быть пустой")
    @Size(max = 50, message = "Фамилия не долна превышать 50 символов")
    private String lastname;
    @NotEmpty(message = "Почта является логином и не долна быть пустой")
    @Email(message = "Неверный шаблон почты")
    private String email;
    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 5, max = 20, message = "Пароль должен содержать от 5 до 20 символов")
    private String password;
    private String password1;
    @NotEmpty(message = "Вы должны выбрать кафедру")
    private String chair;

    public String getChair() {
        return chair;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword1() {
        return password1;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }
}
