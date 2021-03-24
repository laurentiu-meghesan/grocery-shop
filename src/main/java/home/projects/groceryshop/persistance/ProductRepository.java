package home.projects.groceryshop.persistance;

import home.projects.groceryshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
