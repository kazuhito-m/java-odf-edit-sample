package com.github.kazuhito_m.odf_edit_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static final String VERSION = Application.class.getPackage().getImplementationVersion();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
