package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Точка входа в приложение
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
