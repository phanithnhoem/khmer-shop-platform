package biz.phanithnhoem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KhmerShopPlatformApplication implements CommandLineRunner {
	@Value("${app.message.env-profile}")
	private String envProfile;

	public static void main(String[] args) {
		SpringApplication.run(KhmerShopPlatformApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(envProfile);
	}
}
