/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    
    @ManyToOne(targetEntity = Speciality.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Speciality speciality;
    
//    @OneToMany(targetEntity = LessonPlan.class, fetch = FetchType.LAZY, mappedBy = "group")
//    @Cascade({CascadeType.DELETE, CascadeType.SAVE_UPDATE})
//    private Set<LessonPlan> lessonPlans = new HashSet<LessonPlan>();
    
    @ManyToMany(targetEntity = LessonPlan.class, fetch = FetchType.LAZY)
    @JoinTable(name = "lesson_group",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "lesson_id")})
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

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Set<LessonPlan> getLessonPlans() {
        return lessonPlans;
    }

    public void setLessonPlans(Set<LessonPlan> lessonPlans) {
        this.lessonPlans = lessonPlans;
    }
}
