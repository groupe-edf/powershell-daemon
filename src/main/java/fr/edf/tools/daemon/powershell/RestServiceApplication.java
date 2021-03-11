package fr.edf.tools.daemon.powershell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main() of Spring Boot application
 * 
 * @author Mathieu Delrocq
 *
 */
@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}