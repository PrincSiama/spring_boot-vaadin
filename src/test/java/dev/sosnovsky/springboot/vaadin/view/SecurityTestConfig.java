package dev.sosnovsky.springboot.vaadin.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Configuration
public class SecurityTestConfig {
    @Bean
    UserDetailsService mockUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if ("user".equals(username)) {
                    return new User(username, "{noop}user", List.of(
                            new SimpleGrantedAuthority("ROLE_USER")));
                }
                if ("admin".equals(username)) {
                    return new User(username, "{noop}admin", List.of(
                            new SimpleGrantedAuthority("ROLE_ADMIN")));
                }
                throw new UsernameNotFoundException(
                        "User " + username + " not exists");
            }
        };
    }
}
