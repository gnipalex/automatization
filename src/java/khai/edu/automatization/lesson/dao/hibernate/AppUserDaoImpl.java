/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.model.AppUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class AppUserDaoImpl extends HibernateDaoSupport implements AppUserDao{
    @Override
    public AppUser get(Integer id) {
        return (AppUser)this.getHibernateTemplate().get(AppUser.class, id);
    }

    @Override
    public List<AppUser> getAll() {
        return this.getHibernateTemplate().loadAll(AppUser.class);   
    }

    @Override
    public void saveOrUpdate(AppUser obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(AppUser obj) {
        this.getHibernateTemplate().delete(obj);
    }
    
    @Override
    public AppUser getByMail(String mail){
        List<AppUser> users = (List<AppUser>)this.getHibernateTemplate()
                .find("from AppUser u where u.email = ?", mail);
        return users != null && users.size() > 0 ? users.get(0) : null;
    }
}
