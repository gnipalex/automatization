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
@Table(name = "appusers")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name= "lastname", length = 50, nullable = false)
    private String lastName;
    @Column(name = "password", length = 50, nullable = false)
    private String password;
    @Column(name= "isactive")
    private Boolean isActive;
    @Column(name= "expired")
    private Boolean expired;
    
    @Column(name="email", nullable = false, unique = true)
    private String email;
    
    @ManyToOne(targetEntity = Chair.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "readchair_id")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Chair readingChair;
    
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", 
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany(targetEntity = SolutionPlan.class, fetch = FetchType.LAZY, mappedBy = "appUser")
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<SolutionPlan> solutionPlans = new HashSet<SolutionPlan>();

    public Boolean getExpired() {
        return expired;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Chair getReadingChair() {
        return readingChair;
    }

    public void setReadingChair(Chair readingChair) {
        this.readingChair = readingChair;
    }

    public Set<SolutionPlan> getSolutionPlans() {
        return solutionPlans;
    }

    public void setSolutionPlans(Set<SolutionPlan> solutionPlans) {
        this.solutionPlans = solutionPlans;
    }
}
