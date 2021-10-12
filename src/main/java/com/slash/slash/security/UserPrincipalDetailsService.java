package com.slash.slash.security;

import com.slash.slash.exceptions.UserDoesNotExist;
import com.slash.slash.exceptions.UserRoleNotFoundException;
import com.slash.slash.models.Users;
import com.slash.slash.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public UserPrincipalDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Users user = null;
        try {
            user = this.userService.retrieveRealUserByName(name);
        } catch (UserDoesNotExist userDoesNotExist) {
            userDoesNotExist.printStackTrace();
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;

    }

    static public String userIs(Authentication authenticatedUser) throws UserRoleNotFoundException {

        if (authenticatedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return "ADMIN";
        if (authenticatedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")))
            return "USER";
        else
            throw new UserRoleNotFoundException();



    }
}
