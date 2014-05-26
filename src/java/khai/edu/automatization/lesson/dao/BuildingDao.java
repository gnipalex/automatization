package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.Building;

/**
 *
 * @author Alex
 */
public interface BuildingDao extends DaoInterface<Building> {
    public Building getByName(String name);
}
