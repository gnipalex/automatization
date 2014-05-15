/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import java.util.List;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Speciality;

/**
 *
 * @author Alex
 */
public interface SpecialityDao extends DaoInterface<Speciality>{
    public Speciality getByName(String name);
    public List<Speciality> getAllByReadingChair(Chair rd_chair);
}
