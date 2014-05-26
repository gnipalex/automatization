/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.RoomDao;
import khai.edu.automatization.lesson.model.Room;
import khai.edu.automatization.lesson.service.RoomService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
public class RoomServiceImpl implements RoomService {

    private RoomDao roomDao; 
    
    @Override
    public Room getByName(String name) {
        Room r = this.roomDao.getByName(name);
        if (r != null){
            Hibernate.initialize(r.getBuilding());
        }
        return r;
    }

    @Override
    public Room get(Integer id) {
        return this.roomDao.get(id);
    }

    @Override
    public List<Room> getAll() {
        return this.roomDao.getAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(Room obj) {
        this.roomDao.saveOrUpdate(obj);
    }

    @Override
    @Transactional
    public void remove(Room obj) {
        this.roomDao.remove(obj);
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public void setRoomDao(RoomDao roomDao) {
        this.roomDao = roomDao;
    }
}
