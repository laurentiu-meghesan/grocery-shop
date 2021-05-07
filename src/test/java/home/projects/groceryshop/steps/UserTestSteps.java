package home.projects.groceryshop.steps;

import home.projects.groceryshop.domain.User;
import home.projects.groceryshop.service.UserService;
import home.projects.groceryshop.transfer.user.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class UserTestSteps {

    @Autowired
    private UserService userService;

    public User createUser() {
        SaveUserRequest request = new SaveUserRequest();
        request.setUserName("user4546");
        request.setPassword("1234");
        request.setRoles("USER");
        request.setActive(true);

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getUserName(), is(request.getUserName()));
        assertThat(user.getPassword(), is(request.getPassword()));
        assertThat(user.getRoles(), is(request.getRoles()));
        return user;
    }
}
