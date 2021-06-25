package FunFictionUserProject.funFictionUser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    //private static final String ADMIN_ENDPOINT = "/registration";
    //private static final String LOGIN_ENDPOINT = "/comeIn";
   // private static final String SAVE_ENDPOINT = "/save";
   // private static final String MAIN_ENDPOINT = "/";
   // private static final String REGISTRATION_ENDPOINT = "/login";
    private static final String USER_ENDPOINT = "/user/**";
    private static final String ADMIN_ENDPOINT = "/admin/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(USER_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}

 */
