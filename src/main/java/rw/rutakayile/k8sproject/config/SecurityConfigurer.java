package rw.rutakayile.k8sproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rw.rutakayile.k8sproject.dto.ApiResponse;
import rw.rutakayile.k8sproject.filter.JwtRequestFilter;
import rw.rutakayile.k8sproject.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    public static final String[] SWAGGER_URLS =
            new String[]{"/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"};
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**", "/api/v1/public/**", "/api/v1/notifications/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(SWAGGER_URLS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*Add JwtRequestFilter*/
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        /*Exception handling configuration*/
        http.exceptionHandling()
                .authenticationEntryPoint(
                        (req, resp, e) -> {
                            resp.setContentType("application/json;charset=UTF-8");
                            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            resp.getWriter()
                                    .write(
                                            new ApiResponse<Void>(
                                                    HttpStatus.FORBIDDEN, "Access Denied Check the token", null)
                                                    .convertToJson());
                        })
                .accessDeniedHandler(
                        (req, resp, e) -> {
                            resp.setContentType("application/json;charset=UTF-8");
                            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            resp.getWriter()
                                    .write(
                                            new ApiResponse<Void>(
                                                    HttpStatus.FORBIDDEN,
                                                    "Access Denied for resource " + req.getRequestURI(),
                                                    null)
                                                    .convertToJson());
                        });
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}
