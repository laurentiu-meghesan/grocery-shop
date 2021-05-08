package home.projects.groceryshop.service;

import home.projects.groceryshop.domain.User;
import home.projects.groceryshop.exception.ResourceNotFoundException;
import home.projects.groceryshop.exception.UserAlreadyExistAuthenticationException;
import home.projects.groceryshop.persistance.UserRepository;
import home.projects.groceryshop.transfer.user.ChangeUserPasswordRequest;
import home.projects.groceryshop.transfer.user.SaveUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(SaveUserRequest request) throws UserAlreadyExistAuthenticationException {

        if (userRepository.existsByUserName(request.getUserName())) {

            throw new UserAlreadyExistAuthenticationException("User already exists, try picking another user name.");
        } else {

            LOGGER.info("Creating user {}", request.getUserName());
            User user = new User();
            user.setUserName(request.getUserName());
            user.setPassword(request.getPassword());
            user.setRoles(request.getRoles());
            user.setActive(request.isActive());

            return userRepository.save(user);
        }
    }

    public User findUser(long id) {
        LOGGER.info("Retrieving User {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User " + id + " not found."));
    }

    public User changeUserPassword(long id, ChangeUserPasswordRequest request) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            if (user.get().getPassword().equals(request.getOldPassword())) {
                LOGGER.info("Changing password for user {}.", id);

                user.get().setPassword(request.getNewPassword());

                return userRepository.save(user.get());

            } else throw new ResourceNotFoundException("Old password does not match ");

        } else throw new ResourceNotFoundException("User not found.");
    }

    public void deleteUser(long id) {
        LOGGER.info("Deleting user {}.", id);
        userRepository.deleteById(id);
    }
}
