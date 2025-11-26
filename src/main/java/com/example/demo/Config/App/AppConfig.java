package com.example.demo.Config.App;

import com.example.demo.Utils.JWTUtils;
import com.example.demo.Utils.JwtCookieAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JwtCookieAuthFilter jwtCookieAuthFilter (JWTUtils jwtUtils){
        return new JwtCookieAuthFilter(jwtUtils);
    }

}
