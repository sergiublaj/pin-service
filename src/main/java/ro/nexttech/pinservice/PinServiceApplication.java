package ro.nexttech.pinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.nexttech.pinservice.servicelayer.models.calvin.UserEntityCalvin;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication()
public class PinServiceApplication {

	public static final List<UserEntityCalvin> users = new ArrayList<>();

	public static void main(String[] args) {

		users.add(new UserEntityCalvin("ID_1", "John Doe", "johndoe@mail.com", 20));
		users.add(new UserEntityCalvin("ID_2", "Marry Smith", "marrysmith@mail.com", 25));
		users.add(new UserEntityCalvin("ID_3", "Alex Pop", "alexpop@mail.com", 39));
		users.add(new UserEntityCalvin("ID_4", "Nick Bunnyun", "nickbunnyun@mail.com", 38));

		SpringApplication.run(PinServiceApplication.class, args);
	}

}
