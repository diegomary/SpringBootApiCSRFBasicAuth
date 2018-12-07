package dm88;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())    
      .and()
      .authorizeRequests()
      .antMatchers("/api/**").hasRole("ADMIN")
      .and().httpBasic().realmName("DM88.CLOUD-REALM")
      .authenticationEntryPoint(getBasicAuthEntryPoint())
      .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
  }
  
  
  @Bean
  public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
      return new CustomBasicAuthenticationEntryPoint();
  }
  
  
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
     
  } 
 
  
  @Bean
  public UserDetailsService userDetailsService() {
      InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
      manager.createUser(User
        .withUsername("diegomary")
        .password(encoder().encode("**************************************************************"))
        .roles("ADMIN").build());
      manager.createUser(User
        .withUsername("admin")
        .password(encoder().encode("adminPass"))
        .roles("ADMIN").build());
      return manager;
  }  
  
  
  @Bean
  public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
  }
 
  
}
