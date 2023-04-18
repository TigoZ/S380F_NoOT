package hkmu.edu.hk.s380f.noot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers("/blog/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/forgot_password").permitAll()
                        .requestMatchers("/blog/comment/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/blog", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                )
                .httpBasic(withDefaults());
        return http.build();
    }


}
