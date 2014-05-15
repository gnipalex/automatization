/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import java.util.List;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;

/**
 *
 * @author Alex
 */
public interface DisciplineDao extends DaoInterface<Discipline>{
    public List<Discipline> getByName(String name, Chair rd_chair);
    public List<Discipline> getByChair(Chair ch);
}
