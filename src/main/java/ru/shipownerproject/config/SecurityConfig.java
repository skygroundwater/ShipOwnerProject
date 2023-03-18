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

    private static final String DRT = "/director";

    private static final String CNT = "/countries";

    private static final String SWR = "/shipowners";

    private static final String SMN = "/seamen";

    private static final String VSL = "/vessels";

    private static final String PRT = "/ports";

    private static final String IMO = "/{IMO}";

    private static final String name = "/{name}";

    private static final String dlt = "/delete";

    private static final String rfc = "/refactor";

    private static final String id = "/{id}";

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
                            DRT + "/user",
                            DRT + "/admin",
                            DRT + DRT,
                            DRT + dlt + "/{username}").hasRole("DIRECTOR");

                    //admin, director
                    auth.requestMatchers(
                            //countries
                            CNT,
                            CNT + rfc + name,

                            //shipowners
                            SWR,
                            SWR + dlt + name,
                            SWR + rfc + name,

                            //ports
                            PRT,
                            PRT + dlt + name,
                            PRT + rfc + name,

                            //seamen
                            SMN,
                            SMN + dlt + id,
                            SMN + rfc + id,

                            //vessels
                            VSL,
                            VSL + rfc + IMO,
                            VSL + dlt + IMO
                    ).hasAnyRole("ADMIN", "DIRECTOR");

                    //user, admin, director
                    auth.anyRequest().hasAnyRole("USER", "ADMIN", "DIRECTOR");
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
