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

    @ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Group group;
    
    @ManyToOne(targetEntity = Discipline.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Discipline discipline; 

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
    
    @ManyToOne(targetEntity = Speciality.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Speciality speciality; 
    
    @Column(name = "exam", nullable = false)
    private Boolean exam;
    @Column(name = "zachet", nullable = false)
    private Boolean zachet;
    @Column(name = "div_zachet", nullable = false)
    private Boolean div_zachet;
    
    @Column(name = "rgr", nullable = false)
    private Boolean rgr;
    @Column(name = "rr", nullable = false)
    private Boolean rr;
    @Column(name = "rk", nullable = false)
    private Boolean rk;
    @Column(name = "cp", nullable = false)
    private Boolean cp;
    @Column(name = "cw", nullable = false)
    private Boolean cw;
    
    @Column(name = "hoursLect1Half", nullable = false)
    private Integer hoursLect1Half;
    @Column(name = "hoursLect2Half", nullable = false)
    private Integer hoursLect2Half;
    @Column(name = "hoursLab1Half", nullable = false)
    private Integer hoursLab1Half;
    @Column(name = "hoursLab2Half", nullable = false)
    private Integer hoursLab2Half;
    @Column(name = "hoursPract1Half", nullable = false)
    private Integer hoursPract1Half;
    @Column(name = "hoursPract2Half", nullable = false)
    private Integer hoursPract2Half;
    
    @Column(name = "hoursLectAll", nullable = false)
    private Integer hoursLectAll;
    @Column(name = "hoursLabAll", nullable = false)
    private Integer hoursLabAll;
    @Column(name = "hoursPractAll", nullable = false)
    private Integer hoursPractAll;
    
    @Column(name = "hours1Half", nullable = false)
    private Integer hours1Half;
    @Column(name = "hours2Half", nullable = false)
    private Integer hours2Half;
    @Column(name = "hoursAll", nullable = false)
    private Integer hoursAll;
    @Column(name = "hoursOwn", nullable = false)
    private Integer hoursOwn;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Boolean getExam() {
        return exam;
    }

    public void setExam(Boolean exam) {
        this.exam = exam;
    }

    public Boolean getZachet() {
        return zachet;
    }

    public void setZachet(Boolean zachet) {
        this.zachet = zachet;
    }

    public Boolean getDiv_zachet() {
        return div_zachet;
    }

    public void setDiv_zachet(Boolean div_zachet) {
        this.div_zachet = div_zachet;
    }

    public Boolean getRgr() {
        return rgr;
    }

    public void setRgr(Boolean rgr) {
        this.rgr = rgr;
    }

    public Boolean getRr() {
        return rr;
    }

    public void setRr(Boolean rr) {
        this.rr = rr;
    }

    public Boolean getRk() {
        return rk;
    }

    public void setRk(Boolean rk) {
        this.rk = rk;
    }

    public Boolean getCp() {
        return cp;
    }

    public void setCp(Boolean cp) {
        this.cp = cp;
    }

    public Boolean getCw() {
        return cw;
    }

    public void setCw(Boolean cw) {
        this.cw = cw;
    }

    public Integer getHoursLect1Half() {
        return hoursLect1Half;
    }

    public void setHoursLect1Half(Integer hoursLect1Half) {
        this.hoursLect1Half = hoursLect1Half;
    }

    public Integer getHoursLect2Half() {
        return hoursLect2Half;
    }

    public void setHoursLect2Half(Integer hoursLect2Half) {
        this.hoursLect2Half = hoursLect2Half;
    }

    public Integer getHoursLab1Half() {
        return hoursLab1Half;
    }

    public void setHoursLab1Half(Integer hoursLab1Half) {
        this.hoursLab1Half = hoursLab1Half;
    }

    public Integer getHoursLab2Half() {
        return hoursLab2Half;
    }

    public void setHoursLab2Half(Integer hoursLab2Half) {
        this.hoursLab2Half = hoursLab2Half;
    }

    public Integer getHoursPract1Half() {
        return hoursPract1Half;
    }

    public void setHoursPract1Half(Integer hoursPract1Half) {
        this.hoursPract1Half = hoursPract1Half;
    }

    public Integer getHoursPract2Half() {
        return hoursPract2Half;
    }

    public void setHoursPract2Half(Integer hoursPract2Half) {
        this.hoursPract2Half = hoursPract2Half;
    }

    public Integer getHoursLectAll() {
        return hoursLectAll;
    }

    public void setHoursLectAll(Integer hoursLectAll) {
        this.hoursLectAll = hoursLectAll;
    }

    public Integer getHoursLabAll() {
        return hoursLabAll;
    }

    public void setHoursLabAll(Integer hoursLabAll) {
        this.hoursLabAll = hoursLabAll;
    }

    public Integer getHoursPractAll() {
        return hoursPractAll;
    }

    public void setHoursPractAll(Integer hoursPractAll) {
        this.hoursPractAll = hoursPractAll;
    }

    public Integer getHours1Half() {
        return hours1Half;
    }

    public void setHours1Half(Integer hours1Half) {
        this.hours1Half = hours1Half;
    }

    public Integer getHours2Half() {
        return hours2Half;
    }

    public void setHours2Half(Integer hours2Half) {
        this.hours2Half = hours2Half;
    }

    public Integer getHoursAll() {
        return hoursAll;
    }

    public void setHoursAll(Integer hoursAll) {
        this.hoursAll = hoursAll;
    }

    public Integer getHoursOwn() {
        return hoursOwn;
    }

    public void setHoursOwn(Integer hoursOwn) {
        this.hoursOwn = hoursOwn;
    }
}
