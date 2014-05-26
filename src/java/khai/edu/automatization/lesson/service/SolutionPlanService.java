/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.model.SolutionPlan;
import khai.edu.automatization.lesson.web.ajax.AjaxSolution;

/**
 *
 * @author Alex
 */
public interface SolutionPlanService {
    public SolutionPlan get(Integer id);
    public List<SolutionPlan> getAll();
    public void saveOrUpdate(SolutionPlan obj);
    public void remove(SolutionPlan obj);
    
    public SolutionPlan getWithAll(Integer id);
    public boolean createSolution(AjaxSolution ajax_sol);
    public boolean editSolution(AjaxSolution ajax_sol);
    
    public enum SolutionType{
        LECTION, PRACTICE, LAB
    }
    
    public boolean createManySolutions(List<Integer> lab_ids, 
            List<Integer> pract_ids, List<Integer> lect_ids, 
            Integer t_id, Integer r_id);
}
