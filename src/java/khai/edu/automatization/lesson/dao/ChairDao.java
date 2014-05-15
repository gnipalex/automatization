/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import java.util.List;
import khai.edu.automatization.lesson.model.Chair;

/**
 *
 * @author Alex
 */
public interface ChairDao extends DaoInterface<Chair> {
    public Chair getByName(String name);
    public List<Chair> getAllByReadingChair(Chair rd_chair);
}
