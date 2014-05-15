/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.model.Chair;

/**
 *
 * @author Alex
 */
public interface ChairService {
   public Chair getWithSpecialities(int id);
   public Chair getWithTeachers(int id);
   
    public Chair getByName(String name);
    public List<Chair> getAllByReadingChair(Chair rd_chair);
    public Chair get(Integer id);
    public List<Chair> getAll();
    public void saveOrUpdate(Chair obj);
    public void remove(Chair obj);
}
