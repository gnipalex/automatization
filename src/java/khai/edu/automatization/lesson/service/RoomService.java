/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.model.Room;

/**
 *
 * @author Alex
 */
public interface RoomService {
    public Room getByName(String name);
    public Room get(Integer id);
    public List<Room> getAll();
    public void saveOrUpdate(Room obj);
    public void remove(Room obj);
}
