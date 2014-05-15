/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "course", nullable = false)
    private Integer course;
    @Column(name = "spring", nullable = false)
    private Boolean spring;
    
    @OneToMany(targetEntity = LessonPlan.class, fetch = FetchType.LAZY, mappedBy = "semester")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<LessonPlan> lessonPlans = new HashSet<LessonPlan>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LessonPlan> getLessonPlans() {
        return lessonPlans;
    }

    public void setLessonPlans(Set<LessonPlan> lessonPlans) {
        this.lessonPlans = lessonPlans;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Boolean getSpring() {
        return spring;
    }

    public void setSpring(Boolean spring) {
        this.spring = spring;
    }
    
    public void fillBySemester(String s){
        int sem = (int)Double.parseDouble(s);
        this.course = sem / 2 + 1;
        this.spring = sem % 2 == 0;
    }
    
    public String getSeson(){
        return this.spring ? "весна" : "осень";
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder(50);
        str.append(this.name);
        str.append(" ");
        str.append(this.course);
        str.append(" ");
        str.append("курс");
        str.append(" ");
        str.append(this.getSeson());
        return str.toString();
    }
}
