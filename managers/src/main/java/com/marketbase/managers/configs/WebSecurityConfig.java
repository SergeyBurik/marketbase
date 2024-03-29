package com.marketbase.managers.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/", "/registration", "/resources/**",
						"/css/**", "/js/**","/api/**" , "/uploads/**",
						"/api/**", "/search").permitAll()
				.antMatchers("/admin/**").access("hasAuthority('ADMIN')")
				.antMatchers("/manager/**").access("hasAnyAuthority('ADMIN', 'MANAGER')")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
				.logout()
				.permitAll();
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(NoOpPasswordEncoder.getInstance())
				.usersByUsernameQuery("select username, password, active from usr where username=?")
				.authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
		;

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers("/resources/**");
	}
}