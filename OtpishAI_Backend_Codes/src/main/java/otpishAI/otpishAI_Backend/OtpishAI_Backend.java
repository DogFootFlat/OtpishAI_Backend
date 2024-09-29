package otpishAI.otpishAI_Backend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OtpishAI_Backend implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(OtpishAI_Backend.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application Name: " + applicationName);
    }
}