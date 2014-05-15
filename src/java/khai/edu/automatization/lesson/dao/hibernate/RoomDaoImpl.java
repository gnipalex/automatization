/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.RoomDao;
import khai.edu.automatization.lesson.model.Room;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class RoomDaoImpl extends HibernateDaoSupport implements RoomDao{
    @Override
    public Room get(Integer id) {
        return (Room)this.getHibernateTemplate().get(Room.class, id);
    }

    @Override
    public List<Room> getAll() {
        return this.getHibernateTemplate().loadAll(Room.class);   
    }

    @Override
    public void saveOrUpdate(Room obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Room obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Room getByName(String name) {
        List<Room> list = (List<Room>)this.getHibernateTemplate()
                .find("from Room r where r.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null; 
    } 
}
