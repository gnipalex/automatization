/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import khai.edu.automatization.lesson.dao.LazyInitializer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Alex
 */
public class LazyInitializerImpl implements LazyInitializer {
    private SessionFactory sessionFactory;
    //private Session session;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void lazyInit(Object entity, Object lazy) {
        //if (this.session == null){
            Session session = this.sessionFactory.openSession();
        //}
        session.update(entity);
        Hibernate.initialize(lazy);
        session.close();
    }

//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//        if (this.session != null && this.session.isOpen()){
//            this.session.close();
//        }
//    }
    
    
    
}
