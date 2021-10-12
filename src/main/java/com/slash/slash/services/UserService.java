package com.slash.slash.services;

import com.slash.slash.exceptions.NotAuthorized;
import com.slash.slash.exceptions.UserAlreadyExists;
import com.slash.slash.exceptions.UserHasNoName;
import com.slash.slash.exceptions.UserDoesNotExist;
import com.slash.slash.models.Users;
import com.slash.slash.models.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public Users addUser (Users user) throws UserAlreadyExists, UserHasNoName, IOException;
    public void deleteUser(String name, String password) throws UserDoesNotExist, NotAuthorized;
    public Users editUser (String name, Users user) throws UserDoesNotExist, NotAuthorized, UserAlreadyExists;
    public void sendConfirmationEmail(String userEmail) throws IOException;
    public void closeSession (String userEmail);
    public Users changePassword (String name, String userPassword, String newPassword) throws UserDoesNotExist, NotAuthorized;
    public List<UserDto> listUsers();
    public UserDto retrieveUserByName(String name) throws UserDoesNotExist;
    public Users retrieveRealUserByName(String name) throws UserDoesNotExist;
}
