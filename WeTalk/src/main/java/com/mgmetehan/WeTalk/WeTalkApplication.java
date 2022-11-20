package com.mgmetehan.WeTalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("com.mgmetehan.WeTalk.repository")
public class WeTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeTalkApplication.class, args);
    }

}
