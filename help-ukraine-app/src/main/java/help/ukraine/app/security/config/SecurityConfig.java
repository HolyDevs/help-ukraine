package help.ukraine.app.security.config;

import help.ukraine.app.security.filters.AuthenticationFilter;
import help.ukraine.app.security.filters.AuthorizationFilter;
import help.ukraine.app.security.TokenDecoder;
import help.ukraine.app.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static help.ukraine.app.enumerator.AccountType.HOST;
import static help.ukraine.app.enumerator.AccountType.REFUGEE;
import static help.ukraine.app.security.constants.SecurityConstants.*;
import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final TokenDecoder tokenDecoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setUpAuthProcess(http);
        // ~~~~~~~~ SECURITY DEFINITIONS ~~~~~~~~~~
        // USER ENDPOINTS SECURITY ROLES
        defineUserEndpointSecurityRoles(http);
        // OFFER ENDPOINTS SECURITY ROLES
        // todo
        http.authorizeRequests().anyRequest().authenticated();
    }

    private void defineUserEndpointSecurityRoles(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(GET, "user/**").hasAnyAuthority(REFUGEE.name(), HOST.name());
    }

    private void setUpAuthProcess(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(tokenGenerator, authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        http.authorizeRequests()
                .antMatchers(LOGIN_URL + "/**", REFRESH_TOKEN_URL + "/**").permitAll();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(tokenDecoder), UsernamePasswordAuthenticationFilter.class);
    }
}
