package com.ssafy.triptube.configures.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;

import com.ssafy.triptube.configures.jwt.JwtTokenProvider;
import com.ssafy.triptube.configures.jwt.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	private final String[] AUTH_WHITELIST = { "/v2/api-docs", "/v3/api-docs/**", "/configuration/ui",
			"/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/file/**",
			"/image/**", "/swagger/**", "/swagger-ui/**", "/h2/**" };

	private final String[] AUTH_APILIST = { "/api/v1/public/**" };

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll().and().cors()
				.configurationSource(request -> {
					CorsConfiguration cors = new CorsConfiguration();
					cors.setAllowedOrigins(Arrays.asList("*"));
					cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					cors.setAllowedHeaders(Arrays.asList("*"));
					return cors;
				}).and().httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(AUTH_APILIST).permitAll().anyRequest().authenticated().and().addFilterBefore(
						new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
	}
}
