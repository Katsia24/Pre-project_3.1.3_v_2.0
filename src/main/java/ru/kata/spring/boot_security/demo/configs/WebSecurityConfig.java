package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsServiceImp userService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             UserDetailsServiceImp userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }
//    ***************************
//    APPLICATION FAILED TO START
//    ***************************
//
//    Description:
//
//    The dependencies of some of the beans in the application context form a cycle:
//
//            ┌─────┐
//            |  webSecurityConfig defined in file [D:\Java\forKATA\Pre-project\PP_3_1_3_Boot_Security-v2.0\target\classes\ru\kata\spring\boot_security\demo\configs\WebSecurityConfig.class]
//            ↑     ↓
//            |  userServiceImp defined in file [D:\Java\forKATA\Pre-project\PP_3_1_3_Boot_Security-v2.0\target\classes\ru\kata\spring\boot_security\demo\service\UserServiceImp.class]
//            └─────┘
//
//
//    Action:
//
//    Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
//
//    не работает
//    private final UserDetailsService userService;
//
//    @Autowired
//    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsService userService) {
//        this.successUserHandler = successUserHandler;
//        this.userService = userService;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login")
                .permitAll();
    }

    // Настраиваем аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // аутентификация in BD
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setPasswordEncoder(getPasswordEncoder());
//        authProvider.setUserDetailsService(userService);
//        return authProvider;
//    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADMIN", "USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}