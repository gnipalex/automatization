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
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    
    @ManyToOne(targetEntity = Chair.class, fetch = FetchType.LAZY)
    @JoinColumn(name="readchair_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Chair readingChair;
    
    @OneToMany(targetEntity = LessonPlan.class, fetch = FetchType.LAZY, mappedBy = "discipline")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
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

    public Chair getReadingChair() {
        return readingChair;
    }

    public void setReadingChair(Chair readingChair) {
        this.readingChair = readingChair;
    }

    public Set<LessonPlan> getLessonPlans() {
        return lessonPlans;
    }

    public void setLessonPlans(Set<LessonPlan> lessonPlans) {
        this.lessonPlans = lessonPlans;
    } 
}
