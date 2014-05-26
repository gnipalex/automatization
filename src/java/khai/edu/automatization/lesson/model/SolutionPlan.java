/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "solutionplans")
public class SolutionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(targetEntity = LessonPlan.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonplan_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private LessonPlan lessonPlan;
    
    @ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Teacher teacher;
    
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Room room;
    
    @Column(name = "lab")
    private Boolean lab;
    @Column(name = "practice")
    private Boolean practice;
    @Column(name = "lection")
    private Boolean lection;
    
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private AppUser appUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    public void setLessonPlan(LessonPlan lessonPlan) {
        this.lessonPlan = lessonPlan;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Boolean getLab() {
        return lab;
    }

    public void setLab(Boolean lab) {
        this.lab = lab;
    }

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Boolean getLection() {
        return lection;
    }

    public void setLection(Boolean lection) {
        this.lection = lection;
    }
    
    public String getLessonTypes(){
        StringBuilder result = new StringBuilder();
        if (this.lab != null && this.lab.booleanValue()){
            result.append("л.р. ");
        }
        if (this.lection != null && this.lection.booleanValue()){
            result.append("лекц. ");
        }
        if (this.practice != null && this.practice.booleanValue()){
            result.append("пр.");
        }
        return result.toString();
    }
}
