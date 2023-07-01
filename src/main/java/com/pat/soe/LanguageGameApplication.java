package com.pat.soe;

import com.pat.soe.security.RsaKeyProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperty.class)
@SpringBootApplication
public class LanguageGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanguageGameApplication.class, args);
    }

}
