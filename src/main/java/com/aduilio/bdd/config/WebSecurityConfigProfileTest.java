package com.aduilio.bdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.aduilio.bdd.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@Profile("test")
@RequiredArgsConstructor
public class WebSecurityConfigProfileTest {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector)
			throws Exception {
		final MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

		http.authorizeHttpRequests(
				(authorize) -> authorize.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
						.permitAll()
						.requestMatchers(AntPathRequestMatcher.antMatcher("/css/**"))
						.permitAll()
						.requestMatchers(mvcMatcherBuilder.pattern("/login"))
						.permitAll()
						.anyRequest()
						.authenticated())
				.formLogin(form -> form.loginPage("/login")
						.defaultSuccessUrl("/auctions", true)
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout")
						.logoutSuccessUrl("/auctions"))
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return userDetailsServiceImpl;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

//	@Bean
//	public DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
//				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//				.build();
//	}
//
//	@Bean
//	public UserDetailsManager users(final DataSource dataSource) {
//		final UserDetails user = User.withDefaultPasswordEncoder()
//				.username("username")
//				.password("password")
//				.roles("USER")
//				.build();
//
//		final JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//		users.createUser(user);
//		return users;
//	}

//	@Override
//	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}
//
//	@Override
//	protected void configure(final HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//				.antMatchers("/db/**")
//				.permitAll()
//				.antMatchers("/h2-console/**")
//				.permitAll()
//				.antMatchers("/leiloes")
//				.permitAll()
//				.antMatchers("/css/**")
//				.permitAll();
//		;
//
//		http.authorizeRequests()
//				.anyRequest()
//				.authenticated();
//
//		http.formLogin(form -> form.loginPage("/login")
//				.defaultSuccessUrl("/leiloes", true)
//				.permitAll());
//
//		http.logout(logout -> {
//			logout.logoutUrl("/logout")
//					.logoutSuccessUrl("/leiloes");
//		});
//
//		http.headers()
//				.frameOptions()
//				.disable();
//		http.csrf()
//				.disable();
//	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		BCryptPasswordEncoder enconder = passwordEncoder();
//		auth.inMemoryAuthentication().passwordEncoder(enconder)
//			.withUser("fulano").password(enconder.encode("pass")).roles("USER")
//			.and()
//			.withUser("cigano").password(enconder.encode("pass")).roles("USER")
//			.and()
//			.withUser("beltrano").password(enconder.encode("pass")).roles("USER");
//	}

//	@Override
//	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
//

//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}

}