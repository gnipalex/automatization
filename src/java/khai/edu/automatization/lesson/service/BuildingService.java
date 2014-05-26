package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.model.Building;

/**
 *
 * @author Alex
 */
public interface BuildingService {
    public Building get(Integer id);
    public List<Building> getAll();
    public void saveOrUpdate(Building obj);
    public void remove(Building obj);
    public Building getByName(String name);
    public Building getWithRooms(int id);
}
