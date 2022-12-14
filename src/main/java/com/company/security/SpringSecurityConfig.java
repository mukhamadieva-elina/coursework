package com.company.security;

import com.company.security.jwt.JwtSecurityConfigurer;
import com.company.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SpringSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/company/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/company/data/**").permitAll()
                .antMatchers(HttpMethod.POST, "/company/data/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/company/data/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/company/data/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
