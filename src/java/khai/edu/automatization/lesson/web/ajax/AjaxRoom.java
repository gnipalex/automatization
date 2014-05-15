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
public class AjaxRoom {
    
    @NotEmpty
    private String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    
    
}
