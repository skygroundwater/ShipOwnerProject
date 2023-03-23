package ru.shipownerproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(usersService)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {

                    //director
                    auth.requestMatchers(
                            "/director" + "/user",
                            "/director" + "/admin",
                            "/director" + "/director",
                            "/director" + "/{username}").hasRole("DIRECTOR");

                    //admin, director
                    auth.requestMatchers(
                            //countries
                            "/countries",
                            "/countries" + "/refactor" + "/{name}",

                            //shipowners
                            "/shipowners",
                            "/shipowners" + "/{name}",
                            "/shipowners" + "/refactor" + "/{name}",

                            //ports
                            "/ports",
                            "/ports" + "/{name}",
                            "/ports" + "/refactor" + "/{name}",

                            //seamen
                            "/seamen",
                            "/seamen" + "/{passportNumber}",
                            "/seamen" + "/refactor" + "/{passportNumber}",

                            //vessels
                            "/vessels",
                            "/vessels" + "/refactor" + "/{IMO}",
                            "/vessels" + "/{IMO}"
                    ).hasAnyRole("ADMIN", "DIRECTOR");

                    //user, admin, director
                    auth.anyRequest().hasAnyRole("USER", "ADMIN", "DIRECTOR");
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
