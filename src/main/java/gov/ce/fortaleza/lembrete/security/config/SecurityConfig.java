package gov.ce.fortaleza.lembrete.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ContentSecurityPolicyHeaderWriter;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 21:21
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private static final String DEFAULT_SRC_SELF_POLICY = "default-src 'self'; frame-ancestors 'self'; " +
            "form-action 'self'; font-src 'self' fonts.googleapis.com fonts.gstatic.com; " +
            "style-src 'self' 'unsafe-inline' fonts.googleapis.com fonts.gstatic.com";

    public SecurityConfig(UserDetailsService userDetailsService,
                          AuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().addHeaderWriter(writer()).and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/passrecover").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/validatecode/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/changepass").permitAll()
                .antMatchers("/index.html", "/", "/favicon.ico", "/home", "/login",
                        "/*.css", "/*.js*", "/assets/**").permitAll()
                .anyRequest().authenticated().and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    @Primary
    AuthenticationManagerBuilder authenticationManager(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
            throws Exception {
        ActiveDirectoryLdapAuthenticationProvider authenticationProvider =
                new ActiveDirectoryLdapAuthenticationProvider("amc.fortaleza.ce.gov.br", "ldap://172.30.186.40:389/");
        authenticationProvider.setConvertSubErrorCodesToExceptions(true);
        authenticationProvider.setUseAuthenticationRequestCredentials(true);

        return auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
                .and().authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ContentSecurityPolicyHeaderWriter writer() {
        return new ContentSecurityPolicyHeaderWriter(DEFAULT_SRC_SELF_POLICY);
    }
}
