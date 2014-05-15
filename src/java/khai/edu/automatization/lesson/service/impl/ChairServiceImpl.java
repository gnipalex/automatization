/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.service.ChairService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Transactional
public class ChairServiceImpl implements ChairService {

    private ChairDao chairDao;

    @Override
    public Chair getByName(String name) {
        return this.chairDao.getByName(name);
    }

    @Override
    public List<Chair> getAllByReadingChair(Chair rd_chair) {
        return this.chairDao.getAllByReadingChair(rd_chair);
    }

    @Override
    public Chair get(Integer id) {
        return this.chairDao.get(id);
    }

    @Override
    public List<Chair> getAll() {
        return this.chairDao.getAll();
    }

    @Override
    public void saveOrUpdate(Chair obj) {
        this.chairDao.saveOrUpdate(obj);
    }

    @Override
    public void remove(Chair obj) {
        this.chairDao.remove(obj);
    }

    public ChairDao getChairDao() {
        return chairDao;
    }

    public void setChairDao(ChairDao chairDao) {
        this.chairDao = chairDao;
    }

    @Override
    @Transactional
    public Chair getWithSpecialities(int id) {
        Chair ch = this.chairDao.get(id);
        if (ch != null){
            Hibernate.initialize(ch.getSpecialities());
        }
        return ch;
    }

    @Override
    public Chair getWithTeachers(int id) {
        Chair ch = this.chairDao.get(id);
        if (ch != null){
            Hibernate.initialize(ch.getTeachers());
        }
        return ch;
    }

}
