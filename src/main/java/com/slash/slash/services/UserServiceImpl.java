package com.slash.slash.services;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.slash.slash.exceptions.*;
import com.slash.slash.models.Users;
import com.slash.slash.models.UserDto;
import com.slash.slash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sendgrid.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Users addUser(Users user) throws UserAlreadyExists, UserHasNoName, IOException {
        List<UserDto> userList = listUsers();


        if (user.getName() == null) {
            throw new UserHasNoName();
        }
        for (UserDto savedUsers : userList) {
            if (savedUsers.getName().equals(user.getName())) {
                throw new UserAlreadyExists();
            }
        }
        user.setActive(true);
        sendConfirmationEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String name, String password) throws UserDoesNotExist, NotAuthorized {
        Users user = retrieveRealUserByName(name);
        if (user != null) {
            if (passwordEncoder.matches(password,user.getPassword())) {
                userRepository.delete(user);
            } else {
                throw new NotAuthorized();
            }
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public Users editUser(String name, Users user) throws UserDoesNotExist, NotAuthorized, UserAlreadyExists {
        Users oldUser = retrieveRealUserByName(name);
        if (oldUser == null) {
            throw new UserDoesNotExist();
        }

        List<Users> userList = userRepository.findAll();
        for (Users savedUsers : userList) {
            if (savedUsers.getName().equals(user.getName()) && oldUser.getId() != savedUsers.getId()) {
                throw new UserAlreadyExists();
            }
        }

        if (passwordEncoder.matches(user.getPassword(),oldUser.getPassword())) {
            oldUser.setEmail(user.getEmail());
            oldUser.setName(user.getName());
            userRepository.save(oldUser);

        } else {
            throw new NotAuthorized();
        }
        return oldUser;
    }

    @Override
    public void sendConfirmationEmail(String userEmail) throws IOException {

        Email from = new Email("sender@email.com");
        String subject = "Confirmation email";
        Email to = new Email(userEmail);
        Content content = new Content("text/plain", "confirmation email for Slash app");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            throw ex;
        }
    }

    @Override
    public void closeSession(String userEmail) {

    }

    @Override
    public Users changePassword(String name, String userPassword, String newPassword) throws UserDoesNotExist, NotAuthorized {
        Users user = retrieveRealUserByName(name);

        if (!passwordEncoder.matches(userPassword, user.getPassword())){
            throw new NotAuthorized();
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserDto> listUsers() {
        List<Users> userList = userRepository.findAll();
        List<UserDto> userDtoList = new LinkedList<>();

        for (Users user : userList) {
            userDtoList.add(userToUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto retrieveUserByName(String name) throws UserDoesNotExist {

        List<UserDto> userList = listUsers();

        for (UserDto userDto : userList) {
            if (userDto.getName().equals(name)) return userDto;
        }
        throw new UserDoesNotExist();

    }

    private UserDto userToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    @Override
    public Users retrieveRealUserByName(String name) throws UserDoesNotExist {

        List<Users> userList = userRepository.findAll();

        for (Users user : userList) {
            if (user.getName().equals(name)) return user;
        }
        throw new UserDoesNotExist();

    }
}
