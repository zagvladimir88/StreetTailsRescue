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
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserDAOImpl userDAO;
    private final RoleDAO roleDAO;
    private final MailSenderService mailSenderService;

    private static final String ACTIVATION_URL =
            "http://localhost:8080/activate/%s/";



    @SneakyThrows
    @Transactional
    public void register(User user) {
        addRole(user,roleDAO.findRoleByName("ROLE_USER"));
        user.setRegistrationDate(new Timestamp(new Date().getTime()));
        user.setStatus(Status.NOT_ACTIVE);
        user.setActivationCode(UUIDGenerator.generatedUI());
        User newUser = userDAO.create(user);

        if(newUser.getEmail() != null){
            sendEmail(user);
        }
    }

    @Override
    public User findById(Integer userID) {
        return userDAO.findById(userID).orElseThrow(EntityNotFoundException::new);
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
        var userById = userDAO.findById(userID);
        List<Tail> tails = null;
        if(userById.isPresent()){
            tails = userById.get().getTails()
                    .stream()
                    .filter(tail -> tail.getStatus() == Status.ACTIVE)
                    .collect(Collectors.toList());
        }
        return tails;
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
