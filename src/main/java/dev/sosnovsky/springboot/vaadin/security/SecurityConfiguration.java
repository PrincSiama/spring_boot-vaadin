package dev.sosnovsky.springboot.vaadin.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import dev.sosnovsky.springboot.vaadin.vaadin.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.formLogin(login -> login.usernameParameter("email"));
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails user1 =
                User.withUsername("user1@user.ru")
                        .password("{noop}user1")
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.withUsername("user2@user.ru")
                        .password("{noop}user2")
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withUsername("admin@user.ru")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }
}
