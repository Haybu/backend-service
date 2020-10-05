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

	public MyRestController(MyConfig myConfig) {
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

	private String direction = "no direction";
	private int temp;
	private String make = "no make";
	private String model;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}
}

@Component
class MyBean {

	private final MyConfig config;

	public MyBean(MyConfig config) {
		this.config = config;
	}

	@Scheduled(fixedDelay = 5000)
	public void hello() {

		System.out.println("The direction is: " + config.getDirection());
		System.out.println("The make is: " + config.getMake());
	}
}
