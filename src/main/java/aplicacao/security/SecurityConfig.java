package aplicacao.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String[] SWAGGER_WHITELIST = { "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**" };
	

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
		.addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers(SWAGGER_WHITELIST).permitAll()
		.antMatchers("/login").permitAll()

		.antMatchers("/livros").authenticated()
		.antMatchers("/livros/{id}").authenticated()
		.antMatchers("/locacao").authenticated()
		.antMatchers("/cadastro").permitAll()
		.antMatchers("/usuario").authenticated()
		.anyRequest().authenticated()
		.and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}