package home.projects.groceryshop;

import home.projects.groceryshop.persistance.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class GroceryShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryShopApplication.class, args);
	}

}
