package postech.g105.hubens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class HubensApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubensApplication.class, args);
	}

}
