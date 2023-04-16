package com.zagvladimir.service.user;

import com.zagvladimir.model.Role;
import com.zagvladimir.model.Tail;
import com.zagvladimir.model.User;
import com.zagvladimir.model.enums.Status;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.mail.MailSenderService;
import com.zagvladimir.util.UUIDGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private final User testUser;
    private final User testUserNonActivated;
    private final User testUser3;
    private final User testUserBanned;
    private final Role testRole;
    private final Role testRoleAdmin;
    private final Set<Tail> tails;
    private static final String ACTIVATION_URL =
            "http://localhost:8080/activate/%s/";

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private  MailSenderService mailSenderService;
    @Mock
    private UUIDGenerator uuidGenerator;
    @Mock
    private  BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    private UserServiceImplTest(){
        tails = new HashSet<>();
        Tail testTail = new Tail();
        testTail.setType("CAT");
        tails.add(testTail);

        testUser = new User();
        testUser.setRegistrationDate(new Timestamp(new Date().getTime()));
        testUser.setStatus(Status.ACTIVE);
        testUser.setEmail("test@gmail.com");
        testUser.setFirstName("Test");
        testUser.setLogin("Test_Login");
        testUser.setActivationCode("0c0f77f2-7b49-45f0-83b4-95fb44f444ef");
        testUser.setTails(tails);

        testUserNonActivated = new User();
        testUserNonActivated.setLogin("Test2");
        testUserNonActivated.setStatus(Status.NOT_ACTIVE);

        testUser3 = new User();
        testUser3.setLogin("Test3");

        Set<User> userSet = new HashSet<>();
        userSet.add(testUser);

        testRole = new Role();
        testRole.setName("ROLE_USER");
        testRole.setUsers(userSet);

        testRoleAdmin = new Role();
        testRole.setName("ROLE_ADMIN");
        testRole.setUsers(userSet);

        testUserBanned = new User();
        testUserBanned.setStatus(Status.BANNED);
    }


    @Test
    @SneakyThrows
    void register()  {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", testUser.getFirstName());
        templateModel.put("email", testUser.getEmail());
        templateModel.put("url", String.format(ACTIVATION_URL, testUser.getActivationCode()));

        when(uuidGenerator.getUuid()).thenReturn("0c0f77f2-7b49-45f0-83b4-95fb44f444ef");
        when(roleRepository.findRoleByName("ROLE_USER")).thenReturn(testRole);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        userService.register(testUser);

        verify(roleRepository, Mockito.times(1)).findRoleByName("ROLE_USER");
        verify(userRepository, Mockito.times(1)).save(testUser);
        verify(mailSenderService, Mockito.times(1)).sendMessageUsingThymeleafTemplate(testUser.getEmail(),"Activation link",templateModel);
    }

    @Test
    void findById() {
        User expectedUser = testUser;
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(testUser));

        User actualUser = userService.findById(1);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testFailFindByIdWithNonexistentUserID() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(2));
    }

    @Test
    void findUserByLogin() {
        Optional<User> expectedUser = Optional.of(testUser);

        when(userRepository.findUserByLogin(any())).thenReturn(Optional.of(testUser));

        Optional<User> actualUser = userService.findUserByLogin("Test_Login");

        assertEquals(expectedUser, actualUser);
    }



    @Test
    void activateUser() {
        when(userRepository.findUserByActivationCode("test")).thenReturn(Optional.of(testUserNonActivated));
        boolean isActivated = userService.activateUser("test");
        assertTrue(isActivated);
    }

    @Test
    void failActivateUserIfStatusIsntNON_ACTIVE() {
        when(userRepository.findUserByActivationCode("test")).thenReturn(Optional.of(testUserNonActivated));
        boolean isActivated = userService.activateUser("test");
        assertTrue(isActivated);
    }

    @Test
    void getAllTails() {
        List<Tail> expectedTailsList = new ArrayList<>(tails);

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));


        List<Tail> actualTailList = userService.getAllTails(1);

        assertEquals(expectedTailsList,actualTailList);

    }

    @Test
    void findAll() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(testUser);
        expectedUsers.add(testUserNonActivated);
        expectedUsers.add(testUser3);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualTails = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(expectedUsers, actualTails);
    }

    @Test
    void deleteUserById() {
        Integer userId = 1;

        userService.deleteUserById(userId);

        verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void softDeleteUserById() {
        User deletedUser = new User();
        deletedUser.setLogin("test");
        deletedUser.setId(2);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(deletedUser));
        when(userRepository.save(deletedUser)).thenReturn(deletedUser);

        userService.softDeleteUserById(2);

        verify(userRepository, Mockito.times(1)).findById(2);
        verify(userRepository, Mockito.times(1)).save(deletedUser);
    }

    @Test
    void isUserAdmin() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(testRoleAdmin);

        when(roleRepository.findRolesByUserLogin(anyString())).thenReturn(roleList);
        when(roleRepository.findRoleByName(anyString())).thenReturn(testRoleAdmin);

        boolean isAdmin = userService.isUserAdmin("test");

        assertTrue(isAdmin);

    }

    @Test
    void isBanned() {

        when(userRepository.findById(2)).thenReturn(Optional.of(testUserBanned));

        boolean isBanned = userService.isBanned(2);

        assertTrue(isBanned);
    }

    @Test
    void isBannedFailUserNotFound() {

        when(userRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.isBanned(2));
    }

    @Test
    void banUser() {
        User bannedUser = testUser;
        when(userRepository.findById(2)).thenReturn(Optional.of(bannedUser));

        userService.banUser(2);

        verify(userRepository, Mockito.times(1)).findById(2);
        verify(userRepository, Mockito.times(1)).save(testUser);

    }
}