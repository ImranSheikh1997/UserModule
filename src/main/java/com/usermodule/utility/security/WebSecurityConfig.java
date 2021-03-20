//This is Servlet Filter Like Config.
//Filter Comes Before Authentication. it will Filter out requests before it goes to Authentication.

package com.usermodule.utility.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
        @Autowired
        MyUserDetails myUserDetails;
//
//    @Autowired
//    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// configure AuthenticationManager so that it knows from where to load
// user for matching credentials
// Use BCryptPasswordEncoder
        auth.userDetailsService(myUserDetails).passwordEncoder(passwordEncoder);
    }

/*


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Disable CSRF(Cross Site Request Forgery)
        http.csrf().disable();
        //http.cors().disable();

        //Entry Points (Allowing Requests)
        http.authorizeRequests()
                .antMatchers("usermodule/registration/**").permitAll()
                .antMatchers("usermodule/login").permitAll()
                .antMatchers("usermodule/signin").permitAll()
                .antMatchers("usermodule/registration/confirm").permitAll()
                //Disallow everything else...
                .anyRequest().authenticated();

        //No Session Will be Created or  used by Spring Security
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //If User try to access resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

        //Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        //if you want to test api from browser

         http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //   http.httpBasic();
    }




    @Override
    public void configure(WebSecurity web) throws Exception {


        //Allowing swagger to access without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")//
                .antMatchers("usermodule/registration/**")
                .antMatchers("usermodule/login")
                .antMatchers("usermodule/signin")
                .antMatchers("usermodule/registration/confirm")
        //Currently not using H2 console but here is the code for Un-Securing H2 database
          .and()
         .ignoring()


         .antMatchers("/h2-console");;

    }

*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();
    }

    //To decrypt password with length 12
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}





