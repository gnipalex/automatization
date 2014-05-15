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
@Table(name = "lessonplans")
public class LessonPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "hours", nullable = false)
    private Integer hours;
    
    //private Integer 

    @ManyToMany(targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinTable(name = "lesson_group", 
            joinColumns = {@JoinColumn(name = "lesson_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Group> groups = new HashSet<Group>();
    
    @ManyToOne(targetEntity = Discipline.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Discipline discipline; 
    
    @ManyToOne(targetEntity = DiscType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "disctype_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private DiscType discType;

    @ManyToOne(targetEntity = Chair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "prodchair_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Chair producedChair;
    
    @ManyToOne(targetEntity = Chair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "readchair_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Chair readingChair;
    
    @OneToMany(targetEntity = SolutionPlan.class, fetch = FetchType.LAZY, mappedBy = "lessonPlan")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Set<SolutionPlan> solutions = new HashSet<SolutionPlan>();

    @ManyToOne(targetEntity = Semester.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Semester semester;
    
    @ManyToOne(targetEntity = ControlType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "controltype_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private ControlType controlType;
    
    @ManyToOne(targetEntity = Speciality.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Speciality speciality; 
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public DiscType getDiscType() {
        return discType;
    }

    public void setDiscType(DiscType discType) {
        this.discType = discType;
    }

    public Chair getProducedChair() {
        return producedChair;
    }

    public void setProducedChair(Chair producedChair) {
        this.producedChair = producedChair;
    }

    public Chair getReadingChair() {
        return readingChair;
    }

    public void setReadingChair(Chair readingChair) {
        this.readingChair = readingChair;
    }

    public Set<SolutionPlan> getSolutions() {
        return solutions;
    }

    public void setSolutions(Set<SolutionPlan> solutions) {
        this.solutions = solutions;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
