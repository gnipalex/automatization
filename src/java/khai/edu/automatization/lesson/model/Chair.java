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
@Table(name = "chairs")
public class Chair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;
    
    @OneToMany(targetEntity = Speciality.class, fetch = FetchType.LAZY, mappedBy = "produceChair")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Speciality> specialities = new HashSet<Speciality>();
    
    @OneToMany(targetEntity = LessonPlan.class, fetch = FetchType.LAZY, mappedBy = "producedChair")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<LessonPlan> producedLessonPlans = new HashSet<LessonPlan>();
    
    @OneToMany(targetEntity = LessonPlan.class, fetch = FetchType.LAZY, mappedBy = "readingChair")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<LessonPlan> readingLessonPlans = new HashSet<LessonPlan>();
    
    @OneToMany(targetEntity = Discipline.class, fetch = FetchType.LAZY, mappedBy = "readingChair")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Discipline> disciplines = new HashSet<Discipline>();
    
    @OneToMany(targetEntity = Teacher.class, fetch = FetchType.LAZY, mappedBy = "chair")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Teacher> teachers = new HashSet<Teacher>();

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

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Set<LessonPlan> getProducedLessonPlans() {
        return producedLessonPlans;
    }

    public void setProducedLessonPlans(Set<LessonPlan> producedLessonPlans) {
        this.producedLessonPlans = producedLessonPlans;
    }

    public Set<LessonPlan> getReadingLessonPlans() {
        return readingLessonPlans;
    }

    public void setReadingLessonPlans(Set<LessonPlan> readingLessonPlans) {
        this.readingLessonPlans = readingLessonPlans;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

}
