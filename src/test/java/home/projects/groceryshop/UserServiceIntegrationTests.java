package home.projects.groceryshop;

import home.projects.groceryshop.domain.User;
import home.projects.groceryshop.exception.ResourceNotFoundException;
import home.projects.groceryshop.exception.UserAlreadyExistAuthenticationException;
import home.projects.groceryshop.persistance.UserRepository;
import home.projects.groceryshop.service.UserService;
import home.projects.groceryshop.transfer.user.ChangeUserPasswordRequest;
import home.projects.groceryshop.transfer.user.SaveUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Test
    void createUser_whenValidRequest_thenUserIsCreated() {
        createUser();
    }

    private User createUser() {
        SaveUserRequest request = new SaveUserRequest();
        request.setUserName("user7");
        request.setPassword("0000");
        request.setActive(true);
        request.setRoles("USER");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getUserName(), is(request.getUserName()));
        assertThat(user.getPassword(), is(request.getPassword()));
        assertThat(user.getRoles(), is(request.getRoles()));

        return user;
    }

    @Test
    void createUser_whenUserNameAlreadyExists_thenUserAlreadyExistAuthenticationExceptionIsThrown() {
        SaveUserRequest request = new SaveUserRequest();
        request.setUserName("user2");
        request.setPassword("1111");
        request.setActive(true);
        request.setRoles("USER");

        try {
            userService.createUser(request);
        } catch (UserAlreadyExistAuthenticationException e) {
            assertThat(e, notNullValue());
            assertThat("User already exists, try picking another user name.", true);
        }
    }

    @Test
    void changeUserPassword_whenValidRequest_thenReturnUpdatedUser() {

        User user = createUser();

        ChangeUserPasswordRequest request = new ChangeUserPasswordRequest();
        request.setOldPassword(user.getPassword());
        request.setNewPassword("12345");

        User updatedUser = userService.changeUserPassword(user.getId(), request);

        assertThat(updatedUser, notNullValue());
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getPassword(), is(request.getNewPassword()));
    }

    @Test
    void deleteUser_whenExistingUser_thenUserDoesNotExistAnymore() {

        User user = createUser();

        userService.deleteUser(user.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                userService.findUser(user.getId()));
    }
}
