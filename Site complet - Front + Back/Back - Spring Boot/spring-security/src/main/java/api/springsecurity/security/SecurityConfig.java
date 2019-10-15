package api.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // On désactive les sessions
        //http.formLogin();
        http.authorizeRequests().antMatchers("/login/**","/register/**").permitAll(); // tout le monde à le droit de se connecter sur ces chemins
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/task/**").hasAuthority("ADMIN"); // Seulement Admin peut ajouter une task
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthentificationFilter(authenticationManager())); // On appel le filtre JWTAuthentifictionFilter ||debut de l'authentification
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // On appel le filtre pour voir si le token est bon ou pas (une fois que la personne est déja authentifié)
        //super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // Dire a spring comment il va chercher les données
        /*auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER")
                .and()
                .withUser("user").password("{noop}1234").roles("USER"); \\ Ajouter des utilisateurs en brut
         */
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        //super.configure(auth);
    }

}
