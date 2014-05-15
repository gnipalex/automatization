/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.DiscType;

/**
 *
 * @author Alex
 */
public interface DiscTypeDao extends DaoInterface<DiscType>{
    public DiscType getByName(String name);
}
