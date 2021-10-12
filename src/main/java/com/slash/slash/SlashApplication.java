package com.slash.slash;

import com.slash.slash.FileManagement.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SlashApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlashApplication.class, args);
    }

}
