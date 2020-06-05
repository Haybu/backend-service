package io.agilehandy.backendservie;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties(MyConfig.class)
@EnableScheduling
public class BackendServieApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServieApplication.class, args);
	}

}

@RestController
class MyRestController {

	private final MyConfig myConfig;

	MyRestController(MyConfig myConfig) {
		this.myConfig = myConfig;
	}

	@GetMapping
	public String get() {
		return "Hello from backend service";
	}

	@GetMapping("/props")
	public List<String> prop() {
		List<String> props = new ArrayList<>();
		props.add(myConfig.getDirection());
		props.add(String.valueOf(myConfig.getTemp()));
		return props;
	}

}

@ConfigurationProperties(prefix = "prop")
class MyConfig {

	private String direction;
	private int temp;

	String getDirection() {
		return direction;
	}

	void setDirection(String direction) {
		this.direction = direction;
	}

	int getTemp() {
		return temp;
	}

	void setTemp(int temp) {
		this.temp = temp;
	}
}

@Component
class MyBean {

	@Autowired
	private MyConfig config;

	@Scheduled(fixedDelay = 5000)
	public void hello() {
		System.out.println("The message is: " + config.getDirection());
	}
}
