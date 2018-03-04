package realise.example.realiseexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.Message;
import javax.jms.MessageListener;

@SpringBootApplication
public class RealiseExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealiseExampleApplication.class, args);
	}

}
