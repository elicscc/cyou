package com.ccstay.cyou;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class CyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyouApplication.class, args);
    }

//    private String redis_host="127.0.0.1";
//    @Bean
//    public RedisScheduler redisScheduler(){
//        return new RedisScheduler(redis_host);
//    }


}
