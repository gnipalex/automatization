/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.Room;

/**
 *
 * @author Alex
 */
public interface RoomDao extends DaoInterface<Room> {
    public Room getByName(String name);
}
