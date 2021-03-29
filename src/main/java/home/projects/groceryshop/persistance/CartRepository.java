package home.projects.groceryshop.persistance;

import home.projects.groceryshop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
