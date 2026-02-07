package com.example.candystore.application.configuration;


import com.example.candystore.application.jwt.jwtEntryPoint;
import com.example.candystore.application.jwt.jwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private jwtEntryPoint entryPoint;


    @Bean
    public jwtTokenFilter jwtTokenFilter(){return new jwtTokenFilter();}


    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/v2/api-docs", "/swagger-resources/**", "/configuration/ui","/configuration/security", "/swagger-ui.html");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/api/v1/auth/**",
                        "/myapi/**",
                        "/v2/api-docs/**"
                        //"/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


    }
}
