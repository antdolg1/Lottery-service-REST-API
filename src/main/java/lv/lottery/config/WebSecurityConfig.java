package lv.lottery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}adminpass").roles("ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/register", "/status", "/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/stats").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/delete-participant/{id}", "/delete-lottery/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/start-registration", "/stop-registration", "/choose-winner").hasRole("ADMIN")
                .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable();
    }
}
