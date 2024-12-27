package itk.academy.orekhov.springdatajdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Аннотация, которая помечает класс как основной для запуска Spring Boot приложения
public class SpringDataJdbcApplication {

    // Главный метод для запуска приложения
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbcApplication.class, args); // Запуск приложения Spring Boot
    }

}
