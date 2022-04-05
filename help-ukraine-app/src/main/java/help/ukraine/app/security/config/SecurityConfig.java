package help.ukraine.app.security.config;

import help.ukraine.app.security.TokenDecoder;
import help.ukraine.app.security.TokenGenerator;
import help.ukraine.app.security.filters.AuthenticationFilter;
import help.ukraine.app.security.filters.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static help.ukraine.app.security.constants.SecurityConstants.LOGIN_URL;
import static help.ukraine.app.security.constants.SecurityConstants.REFRESH_TOKEN_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
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
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(tokenGenerator, authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        http.authorizeRequests()
                .antMatchers(LOGIN_URL + "/**", REFRESH_TOKEN_URL + "/**").permitAll();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(tokenDecoder), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().anyRequest().authenticated();
    }
}
