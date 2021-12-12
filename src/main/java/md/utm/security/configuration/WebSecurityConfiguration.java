package md.utm.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${csrf-protection.enabled}")
    private boolean csrfProtectionEnabled;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Alice")
                .password(passwordEncoder().encode("alice"))
                .roles("USER")
                .and()
                .withUser("Bob")
                .password(passwordEncoder().encode("bob"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureRoutes(http);
        configureLogin(http);
        configureCsrf(http);
        configureCors(http);
    }

    private void configureRoutes(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/**").hasRole("USER");
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
        if (!csrfProtectionEnabled) {
            http.csrf().disable();
        }
    }

    private void configureLogin(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error=true");
    }

    private void configureCors(HttpSecurity http) throws Exception {
        http.cors();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
