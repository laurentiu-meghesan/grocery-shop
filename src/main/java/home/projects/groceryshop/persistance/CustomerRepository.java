package home.projects.groceryshop.persistance;

import home.projects.groceryshop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
