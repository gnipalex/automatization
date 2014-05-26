/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.ajax;

/**
 *
 * @author Alex
 */
public class AjaxSolution {
    private Integer lesson_id;
    private Integer solution_id;
    private Integer teacher_id;
    private Integer room_id;
    
    private Integer user_id;
    
    private boolean lect;
    private boolean lab;
    private boolean pract;

    public Integer getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Integer lesson_id) {
        this.lesson_id = lesson_id;
    }

    public Integer getSolution_id() {
        return solution_id;
    }

    public void setSolution_id(Integer solution_id) {
        this.solution_id = solution_id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public boolean isLect() {
        return lect;
    }

    public void setLect(boolean lect) {
        this.lect = lect;
    }

    public boolean isLab() {
        return lab;
    }

    public void setLab(boolean lab) {
        this.lab = lab;
    }

    public boolean isPract() {
        return pract;
    }

    public void setPract(boolean pract) {
        this.pract = pract;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
