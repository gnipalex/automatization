/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.ajax;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Alex
 */
public class AjaxTeacher {    
    private Integer id;
    
    @NotEmpty(message = "Фамилия не может быть пустой")
    private String lastName;
    @NotEmpty(message = "Имя не может быть пустым")
    private String firstName;
    @NotEmpty(message = "Отчество не может быть пустым")
    private String middleName;
    @NotEmpty(message = "Должность не может быть пустой")
    private String post;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
}
