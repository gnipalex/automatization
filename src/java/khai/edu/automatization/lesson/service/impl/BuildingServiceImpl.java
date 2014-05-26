package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.BuildingDao;
import khai.edu.automatization.lesson.model.Building;
import khai.edu.automatization.lesson.model.Room;
import khai.edu.automatization.lesson.service.BuildingService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
public class BuildingServiceImpl implements BuildingService {

    private BuildingDao buildingDao;

    @Override
    public Building get(Integer id) {
        return this.buildingDao.get(id);
    }

    @Override
    public List<Building> getAll() {
        return this.buildingDao.getAll();
    }

    @Override
    public void saveOrUpdate(Building obj) {
        this.buildingDao.saveOrUpdate(obj);
    }

    @Override
    public void remove(Building obj) {
        this.buildingDao.remove(obj);
    }

    @Override
    @Transactional
    public Building getWithRooms(int id) {
        Building b = this.get(id);
        if (b != null) {
            Hibernate.initialize(b.getRooms());
            for (Room r : b.getRooms()) {
                Hibernate.initialize(r);
            }
        }
        return b;
    }

    @Override
    public Building getByName(String name) {
        return this.buildingDao.getByName(name);
    }

    public BuildingDao getBuildingDao() {
        return buildingDao;
    }

    public void setBuildingDao(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }
}
