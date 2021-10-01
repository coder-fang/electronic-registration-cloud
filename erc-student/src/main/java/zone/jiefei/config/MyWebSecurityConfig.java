//package zone.jiefei.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author Wangmingcan
// * @date 2021/8/11 9:11
// * @description
// */
//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/students/**")
//                .hasAuthority("salary")
//                .anyRequest().authenticated();
//    }
//}
