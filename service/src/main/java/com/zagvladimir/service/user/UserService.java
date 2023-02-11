package com.zagvladimir.service.user;

import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    void register(User user);
    User findById(Integer userID) throws ChangeSetPersister.NotFoundException;

    boolean activateUser(String code);

    List<Tail> getAllTails(Integer userID);
}
