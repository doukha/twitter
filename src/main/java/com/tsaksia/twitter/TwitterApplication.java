package com.tsaksia.twitter;

import com.tsaksia.twitter.webservice.SocialConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(value = "com.tsaksia.twitter.webservice")
@Import(SocialConfig.class)
public class TwitterApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "twitter-server");
        SpringApplication.run(TwitterApplication.class, args);
    }
}
