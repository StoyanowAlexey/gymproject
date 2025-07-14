package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "org/example/repositories")
@EntityScan(basePackages = "org/example/entities")
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = SpringApplication.run(App.class, args);
    }

}
