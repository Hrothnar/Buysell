package com.neo.buysell.config;

import com.neo.buysell.model.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final SecurityUserService userService;

    private static final String[] AUTH_WHITELIST = {"/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
    };

    private static final String[] PROTECTED = {"/ads/**", "/users/**"};


    @Autowired
    public WebSecurityConfig(SecurityUserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(conf -> conf.mvcMatchers(AUTH_WHITELIST).permitAll());
        http.authorizeHttpRequests(conf -> conf.mvcMatchers(HttpMethod.GET, "/ads/*/image").permitAll());
        http.authorizeHttpRequests(conf -> conf.mvcMatchers(HttpMethod.GET, "/ads").permitAll());
        http.authorizeHttpRequests(conf -> conf.mvcMatchers(PROTECTED).hasAnyRole("USER", "ADMIN"));
//        http.authorizeHttpRequests(conf -> conf.anyRequest().permitAll());
        http.csrf(conf -> conf.disable());
        http.cors(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user@gmail.com")
//                .password("password")
//                .passwordEncoder(plainText -> passwordEncoder().encode(plainText))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
