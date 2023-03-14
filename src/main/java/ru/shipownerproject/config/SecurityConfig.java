package ru.shipownerproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.shipownerproject.services.usersservice.UsersServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UsersServiceImpl usersService;

    @Autowired
    public SecurityConfig(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(usersService)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    //countries
                    auth.requestMatchers("/countries/all", "/countries/{id}").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/countries").hasRole("ADMIN");
                    auth.anyRequest().hasAnyRole("USER", "ADMIN");
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
