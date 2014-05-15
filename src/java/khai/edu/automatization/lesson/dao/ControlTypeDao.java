/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.ControlType;

/**
 *
 * @author Alex
 */
public interface ControlTypeDao extends DaoInterface<ControlType> {
    public ControlType getByName(String name);
}
