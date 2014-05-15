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
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;
    @Column(name = "middleName", nullable = false, length = 50)
    private String middleName;
    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;
    @Column(name = "post", nullable = false, length = 50)
    private String post;
    
    @ManyToOne(targetEntity = Chair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "chair_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Chair chair;
    
    @OneToMany(targetEntity = SolutionPlan.class, fetch = FetchType.LAZY, mappedBy = "teacher")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<SolutionPlan> solutionPlans = new HashSet<SolutionPlan>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Chair getChair() {
        return chair;
    }

    public void setChair(Chair chair) {
        this.chair = chair;
    }

    public Set<SolutionPlan> getSolutionPlans() {
        return solutionPlans;
    }

    public void setSolutionPlans(Set<SolutionPlan> solutionPlans) {
        this.solutionPlans = solutionPlans;
    }
    
    public String getShortFIO(){
        StringBuilder str = new StringBuilder();
        str.append(this.lastName);
        str.append(" ");
        if (this.firstName != null && this.firstName.length() > 0){
            str.append(this.firstName.charAt(0));
            str.append(".");
        }
        if (this.middleName != null && this.middleName.length() > 0){
            str.append(this.middleName.charAt(0));
            str.append(".");
        }
        return str.toString();
    }
}
