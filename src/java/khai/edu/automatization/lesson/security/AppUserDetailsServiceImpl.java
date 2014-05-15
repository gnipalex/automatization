/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.security;

import java.util.ArrayList;
import java.util.List;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Alex
 */
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private AppUserDao appUserDao;
    private LazyInitializer lazyInitializer;
    
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        AppUser user = appUserDao.getByMail(name);
        if (user == null){
            throw new UsernameNotFoundException("User " + name + " not found");
        }
        return this.buildUserPermissions(user);
    }
    
    private UserDetails buildUserPermissions(AppUser entity){
        String username = entity.getEmail();
        String password = entity.getPassword();
        boolean enabled = entity.getIsActive();
        boolean accountNonExpired = !entity.getExpired();
        boolean credentialsNonExpired = !entity.getExpired();
        boolean accountNonLocked = entity.getIsActive();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        lazyInitializer.lazyInit(entity, entity.getRoles());
        
        for (Role role : entity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails user = new User(username, password, enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return user;
    }

    public AppUserDao getAppUserDao() {
        return appUserDao;
    }

    public LazyInitializer getLazyInitializer() {
        return lazyInitializer;
    }

    public void setAppUserDao(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    public void setLazyInitializer(LazyInitializer lazyInitializer) {
        this.lazyInitializer = lazyInitializer;
    }
}
