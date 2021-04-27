package home.projects.groceryshop.persistance;

import home.projects.groceryshop.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);

    boolean existsByUserName(String userName);
}
