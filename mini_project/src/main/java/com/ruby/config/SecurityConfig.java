package com.ruby.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ruby.config.filter.JWTAuthenticationFilter;
import com.ruby.config.filter.JWTAuthorizationFilter;
import com.ruby.persistence.MemberRepo;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfig;
	private final MemberRepo mrepo;
	private final OAuth2SuccessHandler successhandler;
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(cf->cf.disable())
			.cors(cs->cs.configurationSource(corsSource()))
			.httpBasic(bs->bs.disable())
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth->auth
					.requestMatchers("/review/**").authenticated()
					.anyRequest().permitAll()
					);
		
		http.exceptionHandling(ex->ex.authenticationEntryPoint((request, response, authException)->{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}))
			.formLogin(frm->frm.disable())
			.oauth2Login(oauth->oauth
							.permitAll()
							.successHandler(successhandler)
							);
		
		//filters
		http.addFilter(new JWTAuthenticationFilter(authenticationConfig.getAuthenticationManager()));
		http.addFilterAfter(new JWTAuthorizationFilter(mrepo), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		//config.addAllowedOrigin("10.125.121.*"); <- does not work
		config.setAllowedOriginPatterns(Arrays.asList(
		        "http://10.125.121.184.nip.io:3000",
		        "http://10.125.121.*:[*]", // Matches any port on that subnet
		        "https://kdt-mini-front.vercel.app"
		    ));
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		config.setAllowCredentials(true);
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
