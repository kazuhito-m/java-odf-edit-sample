package com.github.kazuhito_m.odf_edit_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class App {

    /**
     * MANUFEST.MF から取れる「(ivyエコシステムの)Jarのバージョン。
     */
    public static final String VERSION = App.class.getPackage().getImplementationVersion();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}
