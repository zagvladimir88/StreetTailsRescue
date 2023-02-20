package com.zagvladimir.service.user;


import com.zagvladimir.dao.RoleDAO;
import com.zagvladimir.dao.UserDAOImpl;
import com.zagvladimir.model.Role;
import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;
import com.zagvladimir.model.enums.Status;
import com.zagvladimir.service.mail.MailSenderService;
import com.zagvladimir.util.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String ACTIVATION_URL =
            "http://localhost:8080/activate/%s/";
    private final UserDAOImpl userDAO;
    private final RoleDAO roleDAO;
    private final MailSenderService mailSenderService;
    private final UUIDGenerator uuidGenerator;

    private final BCryptPasswordEncoder passwordEncoder;


    @SneakyThrows
    @Transactional
    public void register(User user) {
        addRole(user, roleDAO.findRoleByName("ROLE_USER"));
        user.setRegistrationDate(new Timestamp(new Date().getTime()));
        user.setStatus(Status.NOT_ACTIVE);
        user.setActivationCode(uuidGenerator.getUuid());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userDAO.create(user);

        if (newUser.getEmail() != null) {
            sendEmail(user);
        }
    }

    @Override
    public User findById(Integer userID) {
        return userDAO.findById(userID).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userDAO.findUserByLogin(login);
    }

    @Override
    public boolean activateUser(String code) {
        Optional<User> user = userDAO.findUserByActivationCode(code);
        if (user.isPresent() && user.get().getStatus().equals(Status.NOT_ACTIVE)) {
            user.get().setStatus(Status.ACTIVE);
            userDAO.update(user.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Tail> getAllTails(Integer userID) {
        return userDAO.findById(userID)
                .filter(user -> user.getStatus() == Status.ACTIVE)
                .map(User::getTails)
                .map(ArrayList::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public void deleteUserById(Integer userId) {
        userDAO.delete(userId);
    }

    @Override
    public void softDeleteUserById(Integer userId) {
        var userForDelete = userDAO.findById(userId);
        if (userForDelete.isPresent()) {
            userForDelete.get().setStatus(Status.DELETED);
            userDAO.update(userForDelete.get());
        }
    }

    @Override
    public Boolean isUserAdmin(String login) {
        var rolesByUserLogin = roleDAO.findRolesByUserLogin(login);
        var admin = roleDAO.findRoleByName("ROLE_ADMIN");
        return rolesByUserLogin.contains(admin);
    }

    @Override
    public boolean isBanned(Integer userID) {
        var user = userDAO.findById(userID);
        if(user.isPresent()) {
            return user.get().getStatus().equals(Status.BANNED);
        }
        throw new EntityNotFoundException(String.format("User with id:%s not exist",userID));
    }

    @Override
    public void banUser(Integer userID) {
        Optional<User> user = userDAO.findById(userID);
        if (user.isPresent()) {
            user.get().setStatus(Status.BANNED);
            userDAO.update(user.get());
        }
    }

    private void addRole(User user, Role role) {
        Set<Role> rolesList = new HashSet<>();
        rolesList.add(role);
        user.setRoles(rolesList);
        role.getUsers().add(user);
    }


    private void sendEmail(User user) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", user.getFirstName());
        templateModel.put("email", user.getEmail());
        templateModel.put("url", String.format(ACTIVATION_URL, user.getActivationCode()));

        mailSenderService.sendMessageUsingThymeleafTemplate(
                user.getEmail(), "Activation link", templateModel);
    }
}
