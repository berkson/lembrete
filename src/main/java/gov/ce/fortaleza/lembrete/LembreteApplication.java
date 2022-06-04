package gov.ce.fortaleza.lembrete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LembreteApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LembreteApplication.class, args);
    }

}
