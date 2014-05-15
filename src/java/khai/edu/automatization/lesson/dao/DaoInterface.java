/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import java.util.List;

/**
 *
 * @author Alex
 */
public interface DaoInterface<T> {
    public T get(Integer id);
    public List<T> getAll();
    public void saveOrUpdate(T obj);
    public void remove(T obj);
}
