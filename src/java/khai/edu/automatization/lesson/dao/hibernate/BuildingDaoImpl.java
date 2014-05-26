package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.BuildingDao;
import khai.edu.automatization.lesson.model.Building;
import khai.edu.automatization.lesson.model.Chair;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class BuildingDaoImpl extends HibernateDaoSupport implements BuildingDao{

    @Override
    public Building get(Integer id) {
        return (Building)this.getHibernateTemplate().get(Building.class, id);
    }

    @Override
    public List<Building> getAll() {
        return this.getHibernateTemplate().loadAll(Building.class);
    }

    @Override
    public void saveOrUpdate(Building obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Building obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Building getByName(String name) {
        List<Building> list = (List<Building>)this.getHibernateTemplate()
                .find("from Building b where b.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
    
}
