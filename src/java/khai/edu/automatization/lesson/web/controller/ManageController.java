/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import java.util.List;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.AppUserService;
import khai.edu.automatization.lesson.service.RoomService;
import khai.edu.automatization.lesson.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private AppUserService appUserService;
    
    @RequestMapping(value = "/teachers")
    public String manageTeachers(Model model){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        List<Teacher> teachers = teacherService.getByChair(ch);
        model.addAttribute("chair", ch);
        if (teachers != null) {
            model.addAttribute("teachers", teachers);
        }
        return "manage/manage_teachers";
    }
    
    @RequestMapping(value = "/viewTeacher", method = RequestMethod.POST)
    public String getTeacherWindow(Model model, Integer id){
        if (id == null){
            return "manage/teacher_window";
        }
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        Teacher teacher = teacherService.getWithChair(id);
        
        return "manage/teacher_window";
        
    }
    
    @RequestMapping("/rooms")
    public String manageRooms(Model model){
        return "/";
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public AppUserService getAppUserService() {
        return appUserService;
    }

    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    
}
